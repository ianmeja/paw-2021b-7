import {Helmet} from "react-helmet-async";
import {strings} from "../i18n/i18n";
import {Container} from "react-bootstrap";
import RegisterComponent from "../components/Register/Register";
import React from "react";

export default function Register() {
    return (
        <>
            <Helmet>
                <title>{strings.collection.pageTitles.register}</title>
            </Helmet>
            <Container style={{ width: '50%' }} className=' min-height'>
                <RegisterComponent />
            </Container>
        </>
    );
}