import {strings} from "../i18n/i18n";
import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useVerify} from "../api/users/usersSlice";
import {setCredentials} from "../api/auth/authSlice";
import {useEffect, useState} from "react";
import {useAppDispatch} from "../hooks";
import {useLocation} from "react-router";

export default function Verify() {
    const navigate = useNavigate();
    const search = useLocation().search;
    const path_token = new URLSearchParams(search).get("token")

    const [isVerifyError, setIsVerifyError] = useState(false);
    const dispatch = useAppDispatch();

    const [verify, {error}] = useVerify();

    function verifyUser(){
        const rememberMe = true;
        verify({
            token: path_token,
            callback: (token: string | null) => {
                if (token == null) {
                    setIsVerifyError(true)
                    return;
                }
                setIsVerifyError(false)
                dispatch(setCredentials({ token, rememberMe }));
            }
        }).unwrap()
            .catch((error) => setIsVerifyError(true))
    }

    useEffect(() => {
        verifyUser();
    }, [verify]);

    return (
        <div className="container-fluid">
            <div className="row justify-content-center" style={{height: "100vh"}}>
                <div className="col-7 my-auto">
                    <div className="patient-container">
                        {}
                    </div>
                    { isVerifyError && ( <h1><b> {strings.collection.register.invalidToken}</b></h1> )}
                    { !isVerifyError && ( <h1><b> {strings.collection.register.validToken} </b></h1> )}
                    <Button onClick={() => navigate(`/`)} type="submit"
                        className="btn btn-outline-secondary text-white my-10 my-sm-0">
                    {strings.collection.verification.goBack}
                        </Button>
                        </div>
                        </div>
                        </div>
                        );
                    }