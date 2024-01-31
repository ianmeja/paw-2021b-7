import {strings} from "../i18n/i18n";
import {Container} from "react-bootstrap";
import { Helmet } from 'react-helmet-async';
import RestaurantRegisterComponent from "../components/Forms/RestRegisterForm";
import React from "react";

export default function RestaurantRegister() {
    return (
        <>
            <Helmet>
                <title>{strings.collection.pageTitles.register}</title>
            </Helmet>
            <Container style={{ width: '50%' }} className=' min-height'>
                <RestaurantRegisterComponent />
            </Container>
        </>
    );
}