import { Button, Form, Modal } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import {Rating as SimpleStarRating} from 'react-simple-star-rating';
import { strings } from '../../i18n/i18n';
import FormInput from '../FormInputs/FormInputs';
import {useEffect, useState} from 'react';
import {useAddReview} from "../../api/reviews/reviewsSlice";

interface ReviewFormT {
    rating: number;
    message: string;
    restaurantId: number;
    userId: number;
}

function ReviewForm(props: { restaurantId: number, setShow: Function, onRefresh: any}) {

    const loggedUserId = 1

    const [rating, setRating] = useState(0);

    const { restaurantId, setShow, onRefresh } = props;
    const {
        register,
        handleSubmit,
        setValue,
        formState: {errors}
    } = useForm<ReviewFormT>({
        defaultValues: {
            rating: rating,
            message: '',
            restaurantId: restaurantId,
            userId: loggedUserId,
        }
    });

    useEffect(() => setValue('rating', rating), [rating, setValue]);

    const [addReview, result] = useAddReview();

    async function onSubmit(form: ReviewFormT) {
        console.log(form)
        await addReview({
            rest_id: restaurantId,
            text: form.message,
            rating: form.rating,
        });
        onRefresh();
        setShow(false)
    }
    return (
        <div className='modal-content bg-color-grey'>
            <Form onSubmit={handleSubmit(onSubmit)}>
                <>
                    <Modal.Body className='bg-color-grey'>
                                <SimpleStarRating
                                    fillColor='yellow'
                                    onClick={(r) => setRating(r/20)}
                                    initialValue={0}
                                    ratingValue={rating*20}
                                />
                                <FormInput
                                    register={register}
                                    label={strings.collection.reviews.message}
                                    name='message'
                                    type='text'
                                    as="textarea"
                                    error={errors.message}
                                    errorMessage={strings.collection.reviews.error.message}
                                    placeholder={strings.collection.reviews.message}
                                    validation={{
                                        required: true,
                                        maxLength: 100,
                                        minLength: 10
                                    }}
                                />
                    </Modal.Body>
                    <Modal.Footer>
                        <Button type='submit' className='bg-color-action color-primary'>
                            {strings.collection.reviews.review}
                        </Button>
                    </Modal.Footer>
                </>
            </Form>
        </div>
    );
}

export default ReviewForm;
