import {Badge, Button, Card, Col, Modal, Row, Toast, ToastContainer} from 'react-bootstrap';
import { Tabs, Tab, Table } from "react-bootstrap";
import Rating from '../Review/Rating';
import { strings } from '../../i18n/i18n';
import RequestForm from '../Forms/RequestForm';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { userTypes } from '../../views/Restaurant';
import imgdefault from '../../assets/images/img-default.jpg';
import ReviewList from "../Review/ReviewList";
import {WhatsappShareButton} from "react-share";
import {WhatsappIcon} from "react-share";
import ReviewForm from "../Forms/ReviewForm";
import { FaHeart, FaRegHeart} from 'react-icons/fa';
import {Restaurant} from "../../api/restaurants/types";
import useUserId from "../../hooks/useUserId";
import { useAddFavorite, useDeleteFavorite, useCheckFavorite,} from "../../api/favorites/favoritesSlice";
import { useListReviews } from "../../api/reviews/reviewsSlice";
import {Menu} from "../../api/menus/types";
import {useListMenu} from "../../api/menus/menusSlice";
import {useListImages} from "../../api/images/imagesSlice";


function setActiveImage(src: string) {
    const htmlImg = document.getElementById('main-img');
    if (htmlImg === null) {
        return;
    }
    htmlImg.setAttribute('src', src);
}

function MainRestaurantCard(props: {
    restaurant: Restaurant;
} ) {

    const { restaurant } = props;

    let logged = useUserId();

    const [addFavorite, result ] = useAddFavorite();
    const [removeFavorite, resultDelete ] = useDeleteFavorite();
    const [book, setBook] = useState(false);

    let id = restaurant.restId
    let reservation = restaurant.reservation;
    let title = restaurant.fullName;
    let cuisine = restaurant.cuisine;
    let message = restaurant.message;
    let price = restaurant.price;
    let rating = restaurant.rating;
    let neighborhood = restaurant.neighborhood;
    let reviews = restaurant.cantReviews;                // cantidad
    let about = restaurant.about;
    let address = restaurant.address;
    let phoneNumber = restaurant.phoneNumber;

    let userType = 0;               // 1 si es el dueño 2 si es usuario normal 0 si no esta loggeado
    if(logged && (id == logged[0])){
        userType = 1;
    }
    if (logged != null && id != logged[0])
        userType = 2;

    if (!logged){
        logged = [0,0];
    }

    const {data: image, isSuccess: isSuccessImage, refetch: refetchImg } = useListImages({
        id: id
    });

    const navigate = useNavigate();

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => {
        if (userType === userTypes.notLogged) {
            navigate('/login');
        } else setShow(true);
    };

    const { data: menus, isSuccess } = useListMenu(`restaurants/${id}/menu`);


    const [show2, setShow2] = useState(false);
    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => {
        if (userType === userTypes.notLogged) {
            navigate('/login');
        } else setShow2(true);
    };


    const {data: favorites, isSuccess: isSuccessFav, refetch: refetchFav} = useCheckFavorite({
        id: logged[0],
        rest_id: id
    });

    const {data, refetch}= useListReviews({
            rest_id: id
        })

    const addFavorites = async () => {
        if(logged!=null) {
            await addFavorite({
                id: logged[0],
                rest_id: id
            })
            refetchFav();
        }
    };

    const removeFavorites = async () => {
        if(logged!=null) {
            await removeFavorite({
                id: logged[0],
                rest_id: id
            })
            refetchFav();
        }
    };

    return (
        <>
            <Card className='card-style'>
                <Row className='g-0'>
                    <ToastContainer position={"middle-center"}>
                        <Toast onClose={() => setBook(false)} show={book} delay={5000} autohide>
                            <Toast.Header>
                                <strong className="me-auto">Spoon</strong>
                            </Toast.Header>
                            <Toast.Body>{strings.collection.booking.messageConfirm}</Toast.Body>
                        </Toast>
                    </ToastContainer>
                    <Col md={4} lg={6} className='justify-content-center align-items-center'>
                        <Row>
                            <Col>
                        <Card.Title as='h2' className='my-n2 fw-bold'>
                            {title}
                        </Card.Title>
                            </Col>
                            <Col className='col-auto mt-1'>
                                <WhatsappShareButton url={`http://pawserver.it.itba.edu.ar/paw-2021b-7/restaurants/${id}`}>
                                    <WhatsappIcon size={32} round={true}/>
                                </WhatsappShareButton>
                            </Col>
                            { !(logged[1]==1) && userType==2 &&
                            <Col className='col-auto'>
                                {(!isSuccessFav) ? (
                                    <Button onClick={addFavorites} variant="outline-white"><FaRegHeart
                                        className="color-primary" style={{fontSize: 28}}/></Button>
                                ) : (
                                    <Button onClick={removeFavorites} variant="outline-white"><FaHeart
                                        className="color-primary" style={{fontSize: 28}}/></Button>
                                )}
                            </Col>
                            }
                        </Row>
                        <div className='d-flex flex-wrap mt-1'>
                            <Badge
                                className='m-1 bg-color-grey color-spoon-black'
                            >
                                {cuisine}
                            </Badge>
                            <Col/>
                            { reviews != 0 && (
                                <Rating rating={rating} />
                            )}
                        </div>

                        <hr className={'mt-1'}/>

                        <h4 className={'address-text'} >{address} - {neighborhood}</h4>
                        <h4 className={'address-text'} >{phoneNumber}</h4>
                        <h4 className={'address-text'} >{strings.collection.restaurant.priceText} ${price}</h4>
                        <h4 className={'address-text'} >{about}</h4>

                        <h4 className='address-text color-danger'>{message}</h4>

                        <Col className='my-3'>
                            { (logged[1]==1 && id==logged[0]) &&
                            <Button className="text-white"
                                    onClick={() => navigate('/rest-profile')}>{strings.collection.restaurant.edit}</Button>
                            }{!(logged[1]==1) && reservation &&
                                <Button onClick={handleShow} className="text-white" >{strings.collection.restaurant.booking}</Button>
                            }
                        </Col>
                    </Col>
                    <Col md={1} lg={1} />
                    <Col md={4} className='justify-content-center align-items-center'>
                        {isSuccessImage && image && image.length > 0 && (
                            <div>
                                <img
                                    className='img-thumbnail rounded-start article-img'
                                    src={image[0].url.toString()}
                                    alt='articlePicture'
                                    id='main-img'
                                    width='351px'
                                    height='262px'
                                />
                                <div className='d-flex flex-wrap justify-content-center mt-2'>
                                    {image.map((img, i) => (
                                        <button
                                            key={i}
                                            className='btn-link mx-2'
                                            onClick={() => setActiveImage(img.url.toString())}
                                        >
                                            <img
                                                src={img.url.toString()}
                                                width='40px'
                                                height='40px'
                                                alt={'image' + i}
                                            />
                                        </button>
                                    ))}
                                </div>
                            </div>
                        )}
                        {!image &&
                        <div className='d-flex flex-wrap justify-content-center mt-2'>
                            <Card.Img variant='top' src={imgdefault} width='351px' height='262px'/>
                        </div>
                        }
                    </Col>
                </Row>
                {/* TABS */}
                <Tabs defaultActiveKey="menu" id="uncontrolled-tab-example">
                    <Tab eventKey="menu" title="Menu">
                        <div id="cont" className="table">
                            <Table>
                                <thead>
                                <tr>
                                    <th/>
                                    <th className="table-header" scope="col">
                                        {strings.collection.menu.dishName}
                                    </th>
                                    <th className="table-header" scope="col">
                                        {strings.collection.menu.description}
                                    </th>
                                    <th className="table-header"  scope="col">
                                        {strings.collection.menu.price}
                                    </th>
                                </tr>
                                </thead>
                                <tr>
                                    <th className="dish-titles">
                                        {strings.collection.menu.starter}
                                    </th>
                                    <th/>
                                    <th/>
                                    <th/>
                                </tr>
                                <tbody>
                                {isSuccess && menus && menus.length > 0 && ( menus.map((menu: Menu) => (
                                    menu.category == 'STARTER' && (
                                        <tr key={menu.dish_id}>
                                            <td className="dish-titles"/>
                                            <td>
                                                {menu.dish}
                                            </td>
                                            <td>
                                                {menu.description}
                                            </td>
                                            <td>$
                                                {menu.price}
                                            </td>
                                        </tr>
                                    )
                                )))}
                                </tbody>
                                <tr>
                                    <th className="dish-titles">
                                        {strings.collection.menu.main}
                                    </th>
                                    <th/>
                                    <th/>
                                    <th/>
                                </tr>
                                <tbody>
                                {isSuccess && menus && menus.length > 0 && ( menus.map((menu: Menu) => (
                                    menu.category == 'MAIN' && (
                                        <tr key={menu.dish_id}>
                                            <td className="dish-titles"/>
                                            <td>
                                                {menu.dish}
                                            </td>
                                            <td>
                                                {menu.description}
                                            </td>
                                            <td>$
                                                {menu.price}
                                            </td>
                                        </tr>
                                    )
                                )))}
                                </tbody>
                                <tr>
                                    <th className="dish-titles">
                                        {strings.collection.menu.dessert}
                                    </th>
                                    <th/>
                                    <th/>
                                    <th/>
                                </tr>
                                <tbody>
                                {isSuccess && menus && menus.length > 0 && ( menus.map((menu: Menu) => (
                                    menu.category == 'DESSERT' && (
                                        <tr key={menu.dish_id}>
                                            <td className="dish-titles"/>
                                            <td>
                                                {menu.dish}
                                            </td>
                                            <td>
                                                {menu.description}
                                            </td>
                                            <td>$
                                                {menu.price}
                                            </td>
                                        </tr>
                                    )
                                )))}
                                </tbody>
                            </Table>
                        </div>
                    </Tab>
                    <Tab eventKey="reviews" title={strings.collection.restaurant.reviews} className="mb-2">

                        <div className="row d-flex justify-content-end">
                            {!(logged[1]==1) &&
                            <div className="col-auto mb-3 mt-3">
                                <Button onClick={handleShow2}
                                        className="text-white">{strings.collection.restaurant.addReview}</Button>
                            </div>
                            }
                            <div className="col-auto my-auto px-5 mt-3">
                                 {/*{reviews == 1? <h4><small className="text-muted"> {reviews} {strings.collection.restaurant.reviews} </small></h4> : <h4><small className="text-muted"> {reviews} {strings.collection.restaurant.reviews} </small></h4> }*/}
                                { ( reviews > 0) && ( reviews == 1? <h4><small className="text-muted"> {reviews} {strings.collection.restaurant.reviews} </small></h4> : <h4><small className="text-muted"> {reviews} {strings.collection.restaurant.reviews} </small></h4> ) }
                            </div>
                        </div>

                        {(reviews > 0)  && data ? (
                            <>
                                <ReviewList reviews={data} userType={userType}/>
                            </>
                        ) : (
                            <div className='d-flex justify-content-center my-auto'>
                                <p className='lead my-auto'>{strings.collection.noData.noReviews}</p>
                            </div>
                        )}
                    </Tab>
                </Tabs>
            </Card>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header className='bg-color-grey' closeButton>
                    <Modal.Title>{strings.collection.restaurant.booking}</Modal.Title>
                </Modal.Header>
                <RequestForm restaurantId={id} setShow={setShow} setBook={setBook}/>
            </Modal>
            <Modal show={show2} onHide={handleClose2}>
                <Modal.Header className='bg-color-grey' closeButton>
                    <Modal.Title>{strings.collection.restaurant.addReview}</Modal.Title>
                </Modal.Header>
                <ReviewForm restaurantId={id} setShow={setShow2} onRefresh={()=>{ refetch()}}/>
            </Modal>
        </>
    );
}

export default MainRestaurantCard;
