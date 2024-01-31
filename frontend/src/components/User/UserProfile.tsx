import {Button, Card, Col, Form, Modal, Row, Stack, Tab, Table, Tabs} from "react-bootstrap";
import {strings} from "../../i18n/i18n";
import {useForm} from "react-hook-form";
import FormInput from "../FormInputs/FormInputs";
import FavoriteList from '../Favorites/FavoriteList';
import { useUpdateUser } from '../../api/users/usersSlice';
import useUserId from "../../hooks/useUserId";
import PagesList from "../PagesList";
import React, {useEffect, useState} from "react";
import { useListFavorites } from "../../api/favorites/favoritesSlice";
import usePaginatedResponse from "../../hooks/usePaginatedResponse"
import { useListUserBookings} from "../../api/bookings/bookingSlice";
import {Booking} from "../../api/bookings/types";
import {useNavigate} from "react-router";
import {useAppDispatch} from "../../hooks";
import {useDeleteBooking} from "../../api/bookings/bookingSlice";

interface EditForm {
    username: string;
    email: string;
    date: string;
    time: string;
    restaurant: string;
    dinners: number;
    ignored: string;
}

export default function UserProfileComponent(props: {
    username: string, email: string
}

) {

    let id = useUserId();

    if (!id){
        id = [0,0];
    }

    const {username, email} = props;
    const regexp_username = new RegExp(/[a-zA-Z0-9]+/)

    const [page, setPage] = useState(1);
    const [modifyUser, errorUser] = useUpdateUser();
    const { data: bookings, isSuccess: isSuccessBooking, refetch: refetchBook } = useListUserBookings({
        id: id[0]
    });

    const [deleteBooking, resultDelete ] = useDeleteBooking();

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<EditForm>({
        defaultValues: {
            username: username,
            email: email,
        }
    });

    const navigate = useNavigate();
    const dispatch = useAppDispatch()
    const [usernameAlreadyInUse, setUsernameAlreadyInUse] = useState(false);
    const [status, setStatus] = useState(false);


    useEffect(() => {
        if (errorUser.error) {
            setUsernameAlreadyInUse(true);
        }
        else{
            setUsernameAlreadyInUse(false);
        }
    }, [errorUser, dispatch, navigate]);

    function onSubmit(data: EditForm) {
        if(id != null){
            modifyUser({
                id: id[0],
                username: data.username
            })
            setStatus(true)
        }
    }

    useEffect(() => {
        setStatus(false)
    },[props])


    const {data, pages} = usePaginatedResponse(
        useListFavorites({
            id: id[0],
            page_size: 4,
            page: page,
        })
    );

    const [show, setShow] = useState(false);
    const [bookingId, setBookingId] = useState(0);
    const handleClose = () => setShow(false);
    const closeAlert = () => setStatus(false);

    function handleShow(id:number){
        setShow(true);
        setBookingId(id)
    }

    async function deleteBookings(id: number) {
        await deleteBooking({
            booking_id: id
        });
        refetchBook();
        setShow(false);
    }

    return (
        <div className="container ">
            <Card className='mt-4 card-style'>
                <Row>
                    <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example">
                        <Tab eventKey="profile" title={strings.collection.restaurant.profile}>
                            <div className="row d-flex justify-content-between">
                                <div className="col mb-3 ">
                                    <h3 className="heading-small my-4 color-primary fw-bold" style={{textAlign: "center"}}>{strings.collection.restaurant.profile}</h3>
                                    <Form onSubmit={handleSubmit(onSubmit)}>
                                        {(
                                            <>
                                                <Row sm={1} md={2} className='g-3 d-flex justify-content-center'>
                                                    <Col>
                                                        <FormInput
                                                            register={register}
                                                            type='text'
                                                            name='username'
                                                            validation={{ required: true, maxLength: 20, minLength: 4, pattern:regexp_username }}
                                                            label={strings.collection.register.username}
                                                            error={errors.username}
                                                            errorMessage={strings.collection.register.errors.username}
                                                        />
                                                        {usernameAlreadyInUse && (
                                                            <p className='color-danger ms-1'>
                                                                {strings.collection.register.errors.username.validate}
                                                            </p>
                                                        )}
                                                        <Row>
                                                        <Col className="col-auto">
                                                            <label className="mt-4">{strings.collection.register.email}: </label>
                                                        </Col>
                                                        <Col>
                                                            <label className="mt-4">{email}</label>
                                                        </Col>
                                                        </Row>
                                                    </Col>
                                                      <Button type='submit' className='ms-2 mt-5 btn text-white' variant='primary'>
                                                        {strings.collection.register.save}
                                                    </Button>

                                                    <div className="justify-content-center d-flex">

                                                        {status && !usernameAlreadyInUse &&
                                                        <div className="alert alert-warning alert-success alert-dismissible fade show"
                                                             role="alert" style={{color: '#579229'}}>
                                                            {strings.collection.restaurant.messageAlert}
                                                            <button type="button" className="close"
                                                                    data-dismiss="alert"
                                                                    aria-label="Close" onClick={closeAlert}>
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
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
                                                    <td>{booking.restaurant}</td>
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
                                                    <th className="table-header" scope="col">
                                                        <Button type="button" onClick={() => handleShow(booking.booking_id)}  className="btn btn3 btn-outline-dark text-white">{strings.collection.booking.cancel}</Button>
                                                    </th>
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
                        <Tab eventKey="favorites" title={strings.collection.restaurant.favorites}>
                            <div className="row d-flex justify-content-between" >
                                <div className="col mb-3">
                                    <h3 className="heading-small my-4 color-primary fw-bold" style={{textAlign: "center"}}>{strings.collection.restaurant.favorites}</h3>
                                    { ( (id!=null) && data ) ? (
                                        <>
                                            <FavoriteList restaurantsPerRow={4} favorites={data}  />
                                            <PagesList pages={pages} page={page} setPage={setPage} />

                                        </>
                                    ) : (
                                        <div className='d-flex justify-content-center my-auto'>
                                            <p className='lead my-auto'>{strings.collection.noData.noFavorites}</p>
                                        </div>
                                    )}
                                </div>
                            </div>
                        </Tab>
                    </Tabs>
                </Row>
            </Card>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header className='bg-color-grey' closeButton>
                    <Modal.Title>{strings.collection.booking.cancelBooking}</Modal.Title>
                </Modal.Header>
                <Modal.Body>{strings.collection.booking.question}</Modal.Body>
                <Modal.Footer>
                    <Button type='submit' onClick={() => deleteBookings(bookingId)} className='bg-color-action color-primary'>
                        {strings.collection.booking.ok}
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}