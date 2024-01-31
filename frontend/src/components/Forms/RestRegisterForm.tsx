import {Button, Card, Col, Form, Row, Stack} from 'react-bootstrap';
import { strings } from '../../i18n/i18n';
import { useForm } from 'react-hook-form';
import { useEffect, useState } from 'react';
import { useCreateRestaurant } from '../../api/restaurants/restaurantsSlice';
import FormInput from '../FormInputs/FormInputs';
import FormCheckbox from '../FormInputs/FormCheckbox';
import { useAppDispatch } from '../../hooks';
import { setCredentials } from '../../api/auth/authSlice';
import {useNavigate } from 'react-router';
import FormSelect from "../FormInputs/FormSelect";
import {useLogin} from "../../api/authentication/authenticationSlice";

interface RegisterForm {
    email: string;
    name: string;
    phone_number: string;
    password: string;
    neighborhood: string;
    address: string;
    price: number;
    cuisine: string;
    reservation: boolean;
    capacity: number;
}

export default function RestRegisterComponent() {
    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<RegisterForm>({
        defaultValues: {
            email: '',
            password: '',
            name: '',
            neighborhood: '',
            address: '',
            cuisine: '',
        }
    });
    const navigate = useNavigate();
    const dispatch = useAppDispatch();

    const [login] = useLogin();
    const [isLoginError, setIsLoginError] = useState(false);
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const [createRestaurant, result] = useCreateRestaurant();

    const [emailAlreadyInUse, setEmailAlreadyInUse] = useState(false);


    useEffect(() => {
        if (result.error) {
            setEmailAlreadyInUse(true);
        }
        if (result.isSuccess) {
            dispatch(setCredentials({ token: result.data, rememberMe: true }));
            navigate('/');
        }
        const rememberMe = true;
        login({
            email: email,
            password: password,
            callback: (token: string | null) => {
                if (token == null) {
                    setIsLoginError(true);
                    return;
                }

                setIsLoginError(false);
                dispatch(setCredentials({ token, rememberMe }));
            }
        });
    }, [result, dispatch, navigate]);

    function onSubmit(data: RegisterForm) {
        createRestaurant({
            full_name: data.name,
            price: data.price,
            email: data.email,
            phone_number: data.phone_number,
            password: data.password,
            neighborhood: data.neighborhood,
            address: data.address,
            cuisine: data.cuisine,
            reservation: data.reservation,
            capacity: data.capacity
        });
        setEmail(data.email);
        setPassword(data.password);
    }

    return (
        <Card className='shadow card-style create-card mx-3 mt-3'>
            <Card.Body className='form-container'>
                <Form onSubmit={handleSubmit(onSubmit)}>
                    <h3 className='fw-bold my-1'>{strings.collection.register.title}</h3>
                    <p className='my-2'>{strings.collection.register.subtitle}</p>
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
                            validation={{ required: true, minLength: 3, maxLength: 320 }}
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
                            validation={{ required: true, minLength: 6, maxLength: 20 }}
                        />
                        <FormInput
                            register={register}
                            type='text'
                            name='name'
                            label={strings.collection.register.name}
                            placeholder={strings.collection.register.namePlaceholder}
                            error={errors.name}
                            errorMessage={strings.collection.register.errors.name}
                            validation={{ required: true, minLength: 3, maxLength: 30 }}
                        />
                        <FormInput
                            register={register}
                            type='text'
                            name='address'
                            label={strings.collection.register.address}
                            placeholder={strings.collection.register.addressPlaceholder}
                            error={errors.address}
                            errorMessage={strings.collection.register.errors.address}
                            validation={{ required: true, minLength: 8, maxLength: 30 }}
                        />
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
                        <FormInput
                            register={register}
                            type='text'
                            name='neighborhood'
                            label={strings.collection.register.neighborhood}
                            placeholder={strings.collection.register.neighborhoodPlaceholder}
                            error={errors.neighborhood}
                            errorMessage={strings.collection.register.errors.neighborhood}
                            validation={{ required: true, minLength: 3, maxLength: 20 }}
                        />
                        <Col className="col-lg-4">
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
                        <Col className="col-lg-4">
                        <FormSelect
                            register={register}
                            label={strings.collection.register.cuisine}
                            name='cuisine'
                            options={[["aleman","Aleman"],["americano","Americano"],["argentino","Argentino"],
                                ["asiatico","Asiatico"],["autoctono","Autoctono"],["bar","Bar"],["bodegon","Bodegon"],["cerveceria","Cerveceria"],["chino","Chino"],
                                ["de autor","De autor"],["de fusion","De fusion"],["español","Español"],["frances","Frances"],["hamburgueseria","Hamburgueseria"],["indio","Indio"],
                                ["internacional","Internacional"],["israeli","Israeli"],["italiano","Italiano"],["japones","Japones"],["latino","Latino"],["meditarraneo","Meditarraneo"],
                                ["mexicano","Mexicano"],["parrilla","Parrilla"],["peruano","Peruano"],["pescados y mariscos","Pescados y mariscos"],["picadas","Picadas"],["pizzeria","Pizzeria"],
                                ["sin gluten","Sin gluten"],["sushi","Sushi"],["tapeo","Tapeo"],["vegano","Vegano"],["vegetariano","Vegetariano"],["venezolano","Venezolano"],
                            ]}
                        />
                        </Col>
                        <FormCheckbox
                            register={register}
                            label={strings.collection.register.reservation}
                            name='reservation'
                        />
                        <Col className="col-lg-3">
                        <FormInput
                            register={register}
                            label={strings.collection.register.capacityBook}
                            name='capacity'
                            error={errors.capacity}
                            errorMessage={strings.collection.register.errors.capacity}
                            type='number'
                            validation={{ min: 1 }}
                        />
                        </Col>
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