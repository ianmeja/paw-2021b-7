import { Button, Card, Form, Row, Stack } from 'react-bootstrap';
import { strings } from '../../i18n/i18n';
import { useForm } from 'react-hook-form';
import { useEffect, useState } from 'react';
import { useCreateUser } from '../../api/users/usersSlice';
import FormInput from '../FormInputs/FormInputs';
import { useAppDispatch } from '../../hooks';
import { useNavigate } from 'react-router';
import {setCredentials} from "../../api/auth/authSlice";

interface RegisterForm {
    email: string;
    password: string;
    username: string;
}

export default function UserRegisterComponent() {
    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<RegisterForm>({
        defaultValues: {
            email: '',
            password: '',
            username: ''
        }
    });
    const navigate = useNavigate();
    const dispatch = useAppDispatch();

    const [createUser, result] = useCreateUser();

    const [emailAlreadyInUse, setEmailAlreadyInUse] = useState(false);
    const [usernameAlreadyInUse, setUsernameAlreadyInUse] = useState(false);

    useEffect(() => {
        if (result.error) {
            setEmailAlreadyInUse(true);
            setUsernameAlreadyInUse(true);
        }
        if (result.isSuccess) {
            dispatch(setCredentials({ token: result.data, rememberMe: true }));
            navigate('/verification');
        }
    }, [result, dispatch, navigate]);

    function onSubmit(data: RegisterForm) {
        createUser({
            username:  data.username,
            email: data.email,
            password: data.password,
        });
        setEmailAlreadyInUse(false);
        setUsernameAlreadyInUse(false);
    }

    return (
        <Card className='shadow card-style create-card mx-3 mt-3'>
            <Card.Body className='form-container'>
                <Form onSubmit={handleSubmit(onSubmit)}>
                    <h3 className='fw-bold my-1'>{strings.collection.register.title}</h3>
                    <p className='my-2'>{strings.collection.register.userSubtitle}</p>
                    <hr />
                    <Row xs={1} className='g-2'>
                        <FormInput
                            register={register}
                            type='email'
                            name='email'
                            label={strings.collection.login.email}
                            placeholder={strings.collection.login.emailPlaceholder}
                            error={errors.email}
                            errorMessage={strings.collection.login.errors.email}
                            validation={{ required: true, minLength: 8, maxLength: 40 }}
                        />
                        {emailAlreadyInUse && (
                            <p className='color-danger ms-1'>
                                {strings.collection.register.errors.email.validate}
                            </p>
                        )}
                        <FormInput
                            register={register}
                            type='password'
                            name='password'
                            label={strings.collection.register.password}
                            placeholder={strings.collection.register.passwordPlaceholder}
                            error={errors.password}
                            errorMessage={strings.collection.register.errors.password}
                            validation={{ required: true, minLength: 6, maxLength: 100 }}
                        />
                        <FormInput
                            register={register}
                            type='text'
                            name='username'
                            label={strings.collection.register.username}
                            placeholder={strings.collection.register.usernamePlaceholder}
                            error={errors.username}
                            errorMessage={strings.collection.register.errors.username}
                            validation={{ required: true, minLength: 4, maxLength: 30 }}
                        />
                        {usernameAlreadyInUse && (
                            <p className='color-danger ms-1'>
                                {strings.collection.register.errors.username.validate}
                            </p>
                        )}
                        <Stack direction='vertical'>
                            <Button type='submit' className='btn-block bg color-action text-white mt-3 mb-2'>
                                {strings.collection.register.registerButton}
                            </Button>
                        </Stack>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
    );
}