import {Card} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';
import {Restaurant} from '../../api/restaurants/types';
import Rating from '../Review/Rating';
import {GeoAltFill} from 'react-bootstrap-icons';
import {strings} from "../../i18n/i18n";
import imgdefault from '../../assets/images/img-default.jpg';
import {useListImages} from "../../api/images/imagesSlice";

function RestaurantCard(restaurant: Restaurant) {

    const {fullName, description, price , rating, cuisine, neighborhood, restId} = restaurant;

    let navigate = useNavigate();
    const goToRestaurant = () => {
        navigate(`/restaurants/${restId}`);
    };

    const {data: image, isSuccess: isSuccessImage} = useListImages({
        id: restId
    });

    return (
        <Card onClick={goToRestaurant} className='restaurant-card-style text-dark bg-light mb-4'>

            {isSuccessImage && image && image.length && (
                <div className='restaurant-card-img-container'>
                    <Card.Img variant='top' src={image[0].url.toString()}/>
                </div>
            )}

            {!image &&
                <div className='restaurant-card-img-container'>
                    <Card.Img variant='top' src={imgdefault}/>
                </div>
            }

            <Card.Body className='restaurant-card-info-container'>
                <h5 className='text-truncate' style={{fontWeight:"bold"}}>{fullName}</h5>
                <Card.Subtitle className="mb-1 text-muted">{cuisine}</Card.Subtitle>
                <h6 style={{color: "red"}}>{description}</h6>
                <Rating rating={rating}/>
                <Card.Text className="mb-1 text-muted">{strings.collection.restaurant.priceText} ${price}</Card.Text>
                <div className='d-flex mt-1 d-inline-block'>
                    <GeoAltFill size='17px'/>
                    <Card.Text className="mb-1 text-muted">{neighborhood}</Card.Text>
                </div>
            </Card.Body>
        </Card>
    );
}

export default RestaurantCard;