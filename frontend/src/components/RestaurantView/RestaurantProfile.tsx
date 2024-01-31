import {Button, Card, Col, Form, Row, Tab, Table, Tabs} from "react-bootstrap";
import {strings} from "../../i18n/i18n";
import ReviewList from "../Review/ReviewList";
import {useForm} from "react-hook-form";
import FormInput from "../FormInputs/FormInputs";
import FormSelect from "../FormInputs/FormSelect";
import MenuForm from "../Forms/MenuForm";
import React, {useEffect, useState} from "react";
import { useUpdateRestaurant } from '../../api/restaurants/restaurantsSlice';
import {useDeleteMenu, useListMenu} from "../../api/menus/menusSlice";
import {Menu} from "../../api/menus/types";
import {useListReviews} from "../../api/reviews/reviewsSlice";
import {Booking} from "../../api/bookings/types";
import {useConfirmBooking, useListRestaurantBookings} from "../../api/bookings/bookingSlice";
import {useDeleteImage, useListImages} from "../../api/images/imagesSlice";
import ImageInput from "../FormInputs/ImageInput";
import imgdefault from "../../assets/images/img-default.jpg";
import {XCircleFill} from "react-bootstrap-icons";

interface EditForm {
    full_name: string;
    address: string;
    neighborhood: string;
    cuisine: string;
    phone_number: string;
    about: string;
    news: string;
    price: number;
    capacity: number;
    ignored: string;
    images: File[];
    date: string;
    time: string;
    restaurant: string;
    dinners: number;
}

function setActiveImage(src: string) {
    const htmlImg = document.getElementById('main-img');
    if (htmlImg === null) {
        return;
    }
    htmlImg.setAttribute('src', src);
}

export default function RestProfile( props: {
    email: string, full_name: string, phone_number: string, neighborhood: string, address: string, price: number, capacity: number, news: string, about: string, cuisine: string, reservation: boolean, id: number, cant_review: number,
                                     }
){

    let userType = 1;

    const regexp_address = new RegExp(/[a-zA-Z0-9.!#$%@ &'*+/=?^_`{|}~-ñ]+ [0-9]+/);
    const regexp_name = new RegExp(/[a-zA-Z0-9ñ ]+/)

    const {email, full_name, phone_number, neighborhood, address, price, capacity, news, about, cuisine, reservation, id, cant_review} = props;

    let review = cant_review;

    const {data: image, isSuccess: isSuccessImage, refetch: refetchImg } = useListImages({
        id: id
    });

    const [deleteImage, resultDeleteImage] = useDeleteImage();

    async function imageDeleteClick(image_id: number) {
        await deleteImage({
            rest_id: id,
            image_id: image_id
        });
        refetchImg();
    }

    const [status, setStatus] = useState(false);

    const { data: menus, isSuccess: isSuccess, refetch: refetch } = useListMenu(`restaurants/${id}/menu`);

    const { data: bookings, isSuccess: isSuccessBooking, refetch: refetchBook } = useListRestaurantBookings({
        id: id
    });

    const [modifyRestaurant, result] = useUpdateRestaurant();
    const [confirmBooking, resultConfirmBooking] = useConfirmBooking();

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<EditForm>({
        defaultValues: {
            full_name: full_name,
            address: address,
            neighborhood: neighborhood,
            cuisine: cuisine,
            phone_number: phone_number,
            news: news,
            capacity: capacity,
            price: price,
            about: about,
        }
    });

    async function onSubmit(data: EditForm) {
        if(id!=null) {
            await modifyRestaurant({
                id: id,
                full_name: data.full_name,
                phone_number: data.phone_number,
                neighborhood: data.neighborhood,
                address: data.address,
                price: data.price,
                cuisine: data.cuisine,
                capacity: data.capacity,
                message: data.news,
                about: data.about,
                reservation: reservation,
            });
            setStatus(true)
        }
    }

    useEffect(() => {
        setStatus(false)
    },[props])



    async function confirm(id:number){
        await confirmBooking({
            booking_id: id,
        });
        refetchBook();
    }

    const {data}= useListReviews({
        rest_id: id
    })

    const [deleteMenu, deleteMenuResult] = useDeleteMenu();

    async function handleDeleteClick(dish_id: number) {
        await deleteMenu({
            rest_id: id,
            menu_id: dish_id
        });
        refetch();
    }

    const handleClose = () => setStatus(false);


    return (
        <div className="container ">
            <Card className='mt-4 card-style' style={{backgroundColor: '#f8f8f2'}}>
                <Row>
                    <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example">
                        <Tab eventKey="profile" title={strings.collection.restaurant.profile}>
                            <div className="row d-flex justify-content-between">
                                <div className="col mb-3 ">
                                    <h3 className="heading-small my-4 color-primary fw-bold" style={{textAlign: "center"}}>{strings.collection.restaurant.profile}</h3>
                                    <Form onSubmit={handleSubmit(onSubmit)}>
                                        {(
                                            <>
                                                <Row>
                                                    <Row sm={1} md={2} className='g-3 mt-1'>
                                                        <Col>
                                                        <Row>
                                                            <label>Email</label>
                                                        </Row>
                                                        <Row>
                                                            <label className="mt-2">{email}</label>
                                                        </Row>
                                                        </Col>
                                                        <Col>
                                                            <FormInput
                                                                register={register}
                                                                type='text'
                                                                name='full_name'
                                                                validation={{ required: true, maxLength: 20, minLength: 4 ,pattern: regexp_name}}
                                                                error={errors.full_name}
                                                                errorMessage={strings.collection.register.errors.name}
                                                                label={strings.collection.register.name}
                                                            />
                                                        </Col>
                                                        <Col>
                                                            <FormInput
                                                                register={register}
                                                                type='text'
                                                                name='phone_number'
                                                                label={strings.collection.register.phone}
                                                                placeholder={strings.collection.register.phonePlaceholder}
                                                                error={errors.phone_number}
                                                                errorMessage={strings.collection.register.errors.phone}
                                                                validation={{ required: true, minLength: 10, maxLength:  10}}
                                                            />
                                                        </Col>
                                                        <Col>
                                                            <FormInput
                                                                register={register}
                                                                type='text'
                                                                name='address'
                                                                validation={{ required: true, pattern:regexp_address}}
                                                                error={errors.address}
                                                                errorMessage={strings.collection.register.errors.address}
                                                                label={strings.collection.register.address}
                                                            />
                                                        </Col>
                                                        <Col>
                                                            <FormInput
                                                                register={register}
                                                                type='text'
                                                                name='neighborhood'
                                                                validation={{ required: true}}
                                                                error={errors.neighborhood}
                                                                errorMessage={strings.collection.register.errors.neighborhood}
                                                                label={strings.collection.register.neighborhood}
                                                            />
                                                        </Col>
                                                        <Col className="col-lg-2">
                                                            <FormInput
                                                                register={register}
                                                                label={strings.collection.register.price}
                                                                name='price'
                                                                error={errors.price}
                                                                errorMessage={strings.collection.register.errors.price}
                                                                type='number'
                                                                prependIcon='$'
                                                                validation={{ min: 1 }}
                                                            />
                                                        </Col>
                                                        {reservation &&
                                                        <Col className="col-lg-2">
                                                            <FormInput
                                                                register={register}
                                                                label={strings.collection.register.capacityBook}
                                                                name='capacity'
                                                                error={errors.capacity}
                                                                errorMessage={strings.collection.register.errors.capacity}
                                                                type='number'
                                                                validation={{min: 1}}
                                                            />
                                                        </Col>
                                                        }
                                                        <Col className="col-lg-2">
                                                            <FormSelect
                                                                register={register}
                                                                name='cuisine'
                                                                value={'cuisine'}
                                                                disabled={false}
                                                                options={[["aleman","Aleman"],["americano","Americano"],["argentino","Argentino"],
                                                                    ["asiatico","Asiatico"],["autoctono","Autoctono"],["bar","Bar"],["bodegon","Bodegon"],["cerveceria","Cerveceria"],["chino","Chino"],
                                                                    ["de autor","De autor"],["de fusion","De fusion"],["español","Español"],["frances","Frances"],["hamburgueseria","Hamburgueseria"],["indio","Indio"],
                                                                    ["internacional","Internacional"],["israeli","Israeli"],["italiano","Italiano"],["japones","Japones"],["latino","Latino"],["meditarraneo","Meditarraneo"],
                                                                    ["mexicano","Mexicano"],["parrilla","Parrilla"],["peruano","Peruano"],["pescados y mariscos","Pescados y mariscos"],["picadas","Picadas"],["pizzeria","Pizzeria"],
                                                                    ["sin gluten","Sin gluten"],["sushi","Sushi"],["tapeo","Tapeo"],["vegano","Vegano"],["vegetariano","Vegetariano"],["venezolano","Venezolano"],
                                                                ]}
                                                                label={strings.collection.register.cuisine}
                                                            />
                                                        </Col>
                                                        <Col>
                                                            <FormInput
                                                                register={register}
                                                                type='text'
                                                                name='news'
                                                                label={strings.collection.register.new}
                                                            />
                                                        </Col>
                                                        <Col>
                                                            <FormInput
                                                                register={register}
                                                                type='text'
                                                                name='about'
                                                                label={strings.collection.register.about}
                                                            />
                                                        </Col>
                                                    </Row>
                                                    <div className="justify-content-center d-flex">


                                                        <Button type='submit' className='ms-2 mt-5 btn text-white' variant='primary'>
                                                            {strings.collection.register.save}
                                                        </Button>
                                                    </div>

                                                    <div className="justify-content-center d-flex">

                                                    {status &&
                                                            <div className="alert alert-warning alert-success alert-dismissible fade show" role="alert" style={{color: '#579229'}}>
                                                                {strings.collection.restaurant.messageAlert  }
                                                                <Button type="button" className="close text-primary bg-color-cream" data-dismiss="alert" aria-label="Close" onClick={handleClose}>
                                                                    <span aria-hidden="true">&times;</span>
                                                                </Button>
                                                            </div>
                                                            }
                                                    </div>
                                                </Row>
                                            </>
                                        )}
                                    </Form>
                                </div>
                            </div>
                        </Tab>
                        <Tab eventKey="menu" title={strings.collection.restaurant.menu}>
                            <h3 className="heading-small my-4 color-primary fw-bold" style={{textAlign: "center"}}>{strings.collection.restaurant.menu}</h3>
                            <div id="cont" className="table">
                                <Table style={{borderBottomWidth:-1, backgroundColor: "white"}}>
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
                                        <th className="table-header"/>
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
                                                <td>
                                                    <Button className="btn btn3 btn-outline-dark text-white" type="button"
                                                            onClick={() => handleDeleteClick(menu.dish_id)}>{strings.collection.menu.delete}
                                                    </Button>
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
                                                <td>
                                                    <Button className="btn btn3 btn-outline-dark text-white" type="button"
                                                            onClick={() => handleDeleteClick(menu.dish_id)}>{strings.collection.menu.delete}
                                                    </Button>
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
                                                <td>
                                                    <Button className="btn btn3 btn-outline-dark text-white" type="button"
                                                            onClick={() => handleDeleteClick(menu.dish_id)}>{strings.collection.menu.delete}
                                                    </Button>
                                                </td>
                                            </tr>
                                        )
                                    )))}
                                    </tbody>
                                </Table>
                            </div>
                            {id && (
                                <MenuForm restaurantId={id} onRefresh={()=>{ refetch()}}/>
                            )}
                        </Tab>
                        <Tab eventKey="picture" title={strings.collection.restaurant.myPicture}>
                            <div className="row d-flex justify-content-between">
                                <div className="col mb-3">
                                    <h3 className="heading-small my-4 color-primary fw-bold" style={{textAlign: "center"}}>{strings.collection.restaurant.myPicture}</h3>
                                    <div>
                                        <form>
                                            <ImageInput restId={id} onRefreshImg={()=>{ refetchImg()}}/>
                                        </form>
                                    </div>
                                    <div className='d-flex flex-wrap justify-content-center mt-2'>
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
                                                            <Button onClick={() => imageDeleteClick(img.image_id)}    variant='link'
                                                                    className='text-danger fs-6 p-0 m-0 text-decoration-none'

                                                                    ><XCircleFill className='color-spoon-red' /></Button>
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
                                    </div>
                                </div>
                            </div>
                        </Tab>
                        <Tab eventKey="bookings" title={strings.collection.restaurant.bookings}>
                            <div className="row d-flex justify-content-between">
                                <div className="col mb-3">
                                    <h3 className="heading-small my-4 color-primary fw-bold" style={{textAlign: "center"}}>{strings.collection.restaurant.bookings}</h3>

                                    <div id="cont" className="table">
                                        <table id="bookingTable" className="table flex text-center">
                                            {bookings && (
                                            <thead className="thead-light">
                                            <tr>
                                                <th className="table-header" scope="col">
                                                    {strings.collection.booking.user}
                                                </th>
                                                <th className="table-header" scope="col">
                                                    {strings.collection.booking.date2}
                                                </th>
                                                <th className="table-header" scope="col">
                                                    {strings.collection.booking.time2}
                                                </th>
                                                <th className="table-header" scope="col">
                                                    {strings.collection.booking.diners}
                                                </th>
                                                <th className="table-header" scope="col">
                                                    {strings.collection.booking.status}
                                                </th>
                                                <th className="table-header" scope="col"/>
                                            </tr>
                                            </thead>
                                            )}
                                            <tbody>
                                            {bookings && isSuccessBooking && ( bookings.map((booking: Booking) => (
                                                    <tr key = {booking.booking_id}>
                                                        <td>{booking.booker}</td>
                                                        <td>{(new Date(booking.date)).toDateString()}</td>
                                                        <td>{booking.time}</td>
                                                        <td>{booking.diners}</td>
                                                        { booking.confirmed ?
                                                            <td>
                                                                {strings.collection.booking.confirmed}
                                                            </td>
                                                            :
                                                            <td>
                                                                {strings.collection.booking.waiting}
                                                            </td>
                                                        }
                                                        { !booking.confirmed &&
                                                            <th className="table-header" scope="col">
                                                                <Button type="button" onClick={() => confirm(booking.booking_id)} className="btn btn3 btn-outline-dark text-white">{strings.collection.booking.confirm}</Button>
                                                            </th>
                                                        }
                                                    </tr>
                                                )
                                            ))}
                                            </tbody>
                                        </table>
                                    </div>
                                    {!bookings && (
                                        <div className='d-flex justify-content-center my-auto'>
                                            <p className='lead my-auto'>{strings.collection.noData.noBookings}</p>
                                        </div>
                                    )}
                                </div>
                            </div>
                        </Tab>
                        <Tab eventKey="reviews" title={strings.collection.restaurant.reviews}>
                            <div className="row d-flex justify-content-between" >
                                <div className="col mb-3">
                                    <h3 className="heading-small my-4 color-primary fw-bold" style={{textAlign: "center"}}>{strings.collection.restaurant.reviews}</h3>
                                    {((review > 0) && data) ? (
                                        <>
                                            <ReviewList reviews={data} userType={userType} />
                                        </>
                                    ) : (
                                        <div className='d-flex justify-content-center my-auto'>
                                            <p className='lead my-auto'>{strings.collection.noData.noReviews}</p>
                                        </div>
                                    )}
                                </div>
                            </div>
                        </Tab>
                    </Tabs>
                </Row>
            </Card>
        </div>

    )
}