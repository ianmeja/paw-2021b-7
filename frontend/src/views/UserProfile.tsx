import {strings} from "../i18n/i18n";
import {Container} from "react-bootstrap";
import { Helmet } from 'react-helmet-async';
import UserProfileComponent from "../components/User/UserProfile";
import React from "react";
import useUserId from "../hooks/useUserId";
import {useFindUser} from "../api/users/usersSlice";
import {useNavigate} from "react-router";

export default function UserProfile() {

    let id = useUserId();

    if (!id){
        id = [0,0];
    }

    const navigate =  useNavigate();

    const {
        data: user
    } = useFindUser(`users/${id[0]}`);

    if(id[1] == 1 || id[0] == 0){
        navigate("/404");
    }

    return (
        <>
            <Helmet>
                <title>{strings.collection.pageTitles.profile}</title>
            </Helmet>
            <Container style={{ width: '100%' }} className=' min-height'>
                {user && (
                    <UserProfileComponent username={user.username} email={user.email}/>)
                }
            </Container>
        </>
    );
}