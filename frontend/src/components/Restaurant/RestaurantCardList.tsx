import {Row, Col} from 'react-bootstrap';
import RestaurantCard from './RestaurantCard';
import {Restaurant} from '../../api/restaurants/types';

function RestaurantCardList(props: {restaurantsPerRow: Number; restaurants: Restaurant[] }) {
    const {restaurantsPerRow, restaurants} = props;
    return (
        <div className='justify-content-center w-100'>
            <Row md={restaurantsPerRow as number} className='w-100 justify-content-start'>
                {restaurants.map((restaurant) => (
                    <Col key={restaurant.url}>
                        <RestaurantCard {...restaurant}/>
                    </Col>
                ))}
            </Row>
        </div>
    );
}
export default RestaurantCardList;