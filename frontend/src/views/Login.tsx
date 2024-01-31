import LogInComponent from '../components/Forms/LoginForm';
import { Container } from 'react-bootstrap';
import { Helmet } from 'react-helmet-async';
import { strings } from '../i18n/i18n';
import React from 'react';

export default function Login() {
    return (
        <>
            <Helmet>
                <title>{strings.collection.pageTitles.login}</title>
            </Helmet>
            <Container style={{ width: '50%' }} className=' min-height'>
                <LogInComponent />
            </Container>
        </>
    );
}