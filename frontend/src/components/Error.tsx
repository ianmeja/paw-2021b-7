import {strings} from "../i18n/i18n";
import {useNavigate} from "react-router";
import {Button} from "react-bootstrap";

function Error(props: {
    error: number | 'CUSTOM_ERROR' | 'FETCH_ERROR' | 'PARSING_ERROR';
}) {
    const navigate = useNavigate();
    const {error} = props;
    return (
        <div className="container-fluid">
            <div className="row justify-content-center" style={{height:'100vh'}}>
                <div className="col-7 my-auto">
                    <h1 className='fw-bold'>Error {error}</h1>
                    <div className="row">
                        <h1 className="display-5">{strings.collection.errors.pageNotFoundTitle}</h1>
                    </div>
                        <Button className="text-white" onClick={() => navigate("/")}>{strings.collection.errors.pageNotFoundSubtitle}</Button>
                </div>
            </div>
        </div>
    )
}

export default Error;
