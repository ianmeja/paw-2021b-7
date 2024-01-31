import {Card, Container, NavDropdown} from 'react-bootstrap';
import RestaurantCardList from '../components/Restaurant/RestaurantCardList';
import { useListRestaurants } from '../api/restaurants/restaurantsSlice';
import { Helmet } from 'react-helmet-async';
import { strings } from '../i18n/i18n';
import Background from '../assets/images/background-kitchen.jpg';
import useUserId from "../hooks/useUserId";
import {useFindUser} from "../api/users/usersSlice";
import usePaginatedResponse from "../hooks/usePaginatedResponse";
import React, {useState} from "react";
import PagesList from "../components/PagesList";

function Username() {

    let id = useUserId();

    if(!id){
        id = [0,0];
    }

    const { data: user } = useFindUser(`users/${id[0]}`);

    return (
        <h2 className='text-white fw-bold' > { (user && user.username!=null) ? strings.formatString(strings.collection.landing.hello, user.username || '') : '' }</h2>
    );
}

export default function Landing() {

    const [page, setPage] = useState(1);

    const { data: bestRated, pages } = usePaginatedResponse(
        useListRestaurants({
            high_ratings: true,
            page_size: 4,
            page: page,
        })
    );

    const id = useUserId();

    return (
        <>
            <Helmet>
                <title>{strings.collection.pageTitles.home}</title>
            </Helmet>

            <div className="jumbotron mb-0 jumbotron-fluid" style={{backgroundImage: "url(" + Background + ")", height:500, paddingTop:150, backgroundSize:'cover' }}>
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col">
                            <div className="col-md-6 text-light">
                                { id && (id[1]==0) && ( <Username/> )}
                                <h2 className='text-white fw-bold' >{strings.collection.landing.title}</h2>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <Container className='d-flex flex-column align-items-center'>
                <div className='py-2 w-100'>
                    <Card className='card-style'>
                        <Card.Subtitle as='h3'>{strings.collection.landing.bestRated}</Card.Subtitle>
                        <hr/>
                        {bestRated && true && (
                            <>
                                <RestaurantCardList restaurants={bestRated} restaurantsPerRow={4} />
                                <PagesList pages={pages} page={page} setPage={setPage} />
                            </>
                        )}
                    </Card>
                </div>
            </Container>
        </>
    );
}
