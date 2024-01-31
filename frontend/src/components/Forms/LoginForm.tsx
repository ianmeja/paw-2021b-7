import {Button, Card, Col, Form, Row, Stack} from 'react-bootstrap';
import { strings } from '../../i18n/i18n';
import { useForm } from 'react-hook-form';
import { useState } from 'react';
import { useLogin } from '../../api/authentication/authenticationSlice';
import FormInput from '../FormInputs/FormInputs';
import FormCheckbox from '../FormInputs/FormCheckbox';
import { useAppDispatch } from '../../hooks';
import { setCredentials } from '../../api/auth/authSlice';
import { useLocation, useNavigate } from 'react-router';
import {Link} from "react-router-dom";

interface LogInForm {
    email: string;
    password: string;
    rememberMe: boolean;
}

export default function LogInComponent() {

    const [isLoginError, setIsLoginError] = useState(false);
    const [isVerifyError, setIsVerifyError] = useState(false);
    const { state } = useLocation() as { state?: { path?: string } };

    const [login, {error}] = useLogin();
    const navigate = useNavigate();
    const dispatch = useAppDispatch();

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<LogInForm>({
        defaultValues: { email: '', password: '' }
    });


    function useOnSubmit(data: LogInForm) {
        const rememberMe = true;
        login({
            ...data,
            callback: (token: string | null) => {
                if (token == null) {
                    setIsLoginError(true);
                    return;
                }

                setIsLoginError(false);
                dispatch(setCredentials({ token, rememberMe }));
                state?.path ? navigate(state?.path) : navigate(-1);
            }
        }).unwrap()
            .catch((error) => setIsVerifyError(true))
    }

    return (
        <Card className='shadow card-style create-card mx-3 mt-3'>
            <Card.Body className='form-container'>
                <Form onSubmit={handleSubmit(useOnSubmit)}>
                    <h3 className='fw-bold my-1'>{strings.collection.login.title}</h3>
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
                        <FormInput
                            register={register}
                            type='password'
                            name='password'
                            label={strings.collection.login.password}
                            placeholder={strings.collection.login.passwordPlaceholder}
                            error={errors.password}
                            errorMessage={strings.collection.login.errors.password}
                            validation={{ required: true, minLength: 6, maxLength: 30 }}
                        />
                        <FormCheckbox
                            register={register}
                            label={strings.collection.login.rememberMe}
                            name='rememberMe'
                        />
                        {isLoginError && <p className='text-danger'>{strings.collection.login.errors.loginFail}</p>}
                        {isVerifyError && <p className='text-danger'>{strings.collection.login.errors.userDisabled}</p>}
                        <Stack direction='vertical'>
                            <Button type='submit' className='btn-block bg color-action mt-3 mb-2'>
                                {strings.collection.login.loginButton}
                            </Button>
                        <Row>
                            <Col className='mt-2 col-auto'>{strings.collection.login.signupMessage}</Col>
                            <Col>
                                <Link to='/register'>
                                    <button type='button' className='bg btn nav-link text-blue text-decoration-underline'>
                                    {strings.collection.login.signupButton}
                                    </button>
                                </Link>
                            </Col>
                        </Row>
                        </Stack>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
    );
}