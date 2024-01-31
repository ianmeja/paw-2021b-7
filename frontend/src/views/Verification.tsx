import {strings} from "../i18n/i18n";
import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

export default function Verification() {
    const navigate = useNavigate();
    return (
        <div className="container-fluid">
            <div className="row justify-content-center" style={{height: "100vh"}}>
                <div className="col-7 my-auto">
                    <h1><b>
                        {strings.collection.verification.success}
                    </b></h1>
                    <div className="row">
                        <h5 className="display-5">
                            {strings.collection.verification.message}
                        </h5>
                    </div>
                    <Button onClick={() => navigate(`/`)} type="submit"
                            className="btn btn-outline-secondary text-white my-10 my-sm-0">
                        {strings.collection.verification.goBack}
                    </Button>
                </div>
            </div>
        </div>
    );
}