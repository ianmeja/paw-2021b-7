import { Button, Form, Modal } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { strings } from '../../i18n/i18n';
import FormInput from '../FormInputs/FormInputs';
import {useAnswerReview} from "../../api/reviews/reviewsSlice";

interface AnswerReviewFormT {
    message: string;
    reviewId: number;
}

function AnswerReviewForm(props: { restaurantId: number, reviewId: number, setShow: Function }) {

    const { restaurantId, reviewId, setShow } = props;

    const {
        register,
        handleSubmit,
        setValue,
        formState: {errors}
    } = useForm<AnswerReviewFormT>({
        defaultValues: {
            message: '',
            reviewId: reviewId,
        }
    });

    const [addAnswerReview, result] = useAnswerReview();

    function onSubmit(form: AnswerReviewFormT) {
        addAnswerReview({
            ans_id: reviewId,
            rest_id: restaurantId,
            text: form.message,
        });
        setShow(false)
    }
    return (
        <div className='modal-content bg-color-grey'>
            <Form onSubmit={handleSubmit(onSubmit)}>
                <>
                    <Modal.Body className='bg-color-grey'>
                        <FormInput
                            register={register}
                            label={strings.collection.reviews.answer}
                            name='message'
                            type='text'
                            as="textarea"
                            error={errors.message}
                            errorMessage={strings.collection.reviews.error.message}
                            validation={{
                                required: true,
                                maxLength: 100,
                                minLength: 5
                            }}
                        />
                    </Modal.Body>
                    <Modal.Footer>
                        <Button type='submit' className='bg-color-action color-primary'>
                            {strings.collection.reviews.addanswer}
                        </Button>
                    </Modal.Footer>
                </>
            </Form>
        </div>
    );
}

export default AnswerReviewForm;
