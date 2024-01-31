import {strings} from "../i18n/i18n";
import {Container} from "react-bootstrap";
import { Helmet } from 'react-helmet-async';
import RestProfile from "../components/RestaurantView/RestaurantProfile";
import React from "react";
import useUserId from "../hooks/useUserId";
import {useFindRestaurant} from "../api/restaurants/restaurantsSlice";
import {useNavigate} from "react-router";

export default function RestaurantProfile() {

    let id = useUserId();

    const navigate =  useNavigate();

    if(!id){
        id = [0,0];
    }

    const {
        data: rest
    } = useFindRestaurant(`restaurants/${id[0]}`);

    if(id[1] == 0 || id[0] == 0){
        navigate("/404");
    }

    return (
        <>
            <Helmet>
                <title>{strings.collection.pageTitles.profile}</title>
            </Helmet>
            <Container style={{ width: '100%' }} className=' min-height'>
                {rest && (
                    <RestProfile email={rest.email} full_name={rest.fullName} phone_number={rest.phoneNumber} neighborhood={rest.neighborhood} address={rest.address} price={rest.price}  capacity={rest.capacity} news={rest.message} about={rest.about} cuisine={rest.cuisine} reservation={rest.reservation} id={rest.restId} cant_review={rest.cantReviews}/>
                )}
            </Container>
        </>
    );
}