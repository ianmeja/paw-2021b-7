import { Container } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import MainRestaurantCard from '../components/RestaurantView/MainRestaurantCard';
import { useFindRestaurant } from '../api/restaurants/restaurantsSlice';
import { Helmet } from 'react-helmet-async';
import { useEffect } from 'react';
import { useNavigate } from 'react-router';

export const userTypes = {
    notLogged: 0,
    owner: 1,
    notOwner: 2
};

function Restaurant() {
    let { id } = useParams();

    const navigate =  useNavigate();

    const {
        data: restaurant,
        refetch: refetchRest,
        error: errorRest
    } = useFindRestaurant(`restaurants/${id}`);

    useEffect(() => {
        refetchRest();
    }, [restaurant])

    if(errorRest){
        navigate("/404");
    }

    return (
        <>
            <Helmet>
                {restaurant && (
                    <title>{restaurant.fullName}</title>
                )}
            </Helmet>
            <Container className='min-height'>
                {restaurant && (
                    <MainRestaurantCard
                        restaurant={restaurant}
                    />
                )}
            </Container>
        </>
    );
}

export default Restaurant;
