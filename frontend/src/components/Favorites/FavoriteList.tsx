import {Row, Col} from 'react-bootstrap';
import RestaurantCard from '../Restaurant/RestaurantCard';
import {Restaurant} from '../../api/restaurants/types';


function FavoriteList(props: {restaurantsPerRow: Number  ,favorites: Restaurant[]}) {
    const {restaurantsPerRow, favorites } = props;
    return (
        <div className='justify-content-center w-100'>
            <Row md={restaurantsPerRow as number} className='w-100 justify-content-start'>
                {favorites.map((favorites) => (
                <Col key={favorites.url}>
                    <RestaurantCard {...favorites}/>
                </Col>
                ))}
            </Row>
        </div>
    );
}

export default FavoriteList;