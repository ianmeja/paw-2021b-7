import {Button, Col, Form, Modal, Row, Toast, ToastContainer} from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { strings } from '../../i18n/i18n';
import FormInput from '../FormInputs/FormInputs';
import FormSelect from "../FormInputs/FormSelect";
import {useAddBooking} from "../../api/bookings/bookingSlice";
import React from "react";

interface RequestFormT {
    date: Date;
    time: number;
    restaurantId: number;
    dinners: number;
}

function RequestForm(props: { restaurantId: number, setShow: Function, setBook: Function}) {

    const {restaurantId,setShow, setBook} = props;


    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<RequestFormT>({
        defaultValues: {
            date: undefined,
            time: undefined,
            restaurantId: restaurantId,
            dinners: undefined
        }
    });

    const [addBooking, result] = useAddBooking();

    function onSubmit(data: RequestFormT) {
        addBooking({
            diners: data.dinners.toString(),
            time: data.time.toString(),
            date: new Date(data.date),
            rest_id: restaurantId
        });
        setShow(false);
        setBook(true);
    }

    return (
        <div className='modal-content bg-color-grey'>
            <Form onSubmit={handleSubmit(onSubmit)}>
                    <>
                        <Modal.Body className='bg-color-grey'>
                            <Row>
                                <Col md={6} lg={6} className='my-2'>
                                    <FormInput
                                        register={register}
                                        label={strings.collection.booking.date}
                                        name='date'
                                        type='date'
                                        validation={{ required: true }}
                                        error={errors.date}
                                        errorMessage={strings.collection.booking.error.date}
                                    />
                                </Col>
                                <Col md={6} lg={6} className='my-2'>
                                    <FormSelect
                                        register={register}
                                        label={strings.collection.booking.time}
                                        name='time'
                                        value={'dinners'}
                                        validation={{ required: true}}
                                        disabled={false}
                                        error={errors.time}
                                        errorMessage={strings.collection.booking.error.time}
                                        options={[["12:00","12:00"],["12:30","12:30"],["13:00","13:00"],["13:30","13:30"],["14:00","14:00"],["14:30","14:30"],["15:00","15:00"],
                                            ["20:00","20:00"],["20:30","20:30"],["21:00","21:00"],["21:30","21:30"],["22:00","22:00"]]}
                                    />
                                </Col>
                            </Row>
                            <FormSelect
                                register={register}
                                name='dinners'
                                value={'dinners'}
                                disabled={false}
                                options={[["1","1"],["2","2"],["3","3"],["4","4"],["5","5"],["6","6"],["7","7"],["8","8"],["9","9"],["10","10"]]}
                                label={strings.collection.booking.dinners}
                            />
                        </Modal.Body>
                        <Modal.Footer>
                            <Button type='submit' className='bg-color-action color-primary'>
                                {strings.collection.booking.book}
                            </Button>
                        </Modal.Footer>
                    </>
            </Form>
        </div>
    );
}

export default RequestForm;
