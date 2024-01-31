import { Card } from 'react-bootstrap';
import { strings } from '../../i18n/i18n';
import {useNavigate} from "react-router-dom";
import userRegister from "../../assets/images/user_register.png";
import restRegister from "../../assets/images/restaurant_register.jpeg";

export default function Register() {
    let title = "Cliente"
    let description = "Únete para descubrir los mejores restaurantes"
    let title_rest = "Restaurante"
    let description_rest = "Registra tu propio restaurant para que más gente lo conozca"

    let navigate = useNavigate();

    const goToUserRegister = () => {
        navigate('/user-register');
    };

    const goToRestRegister = () => {
        navigate('/rest-register');
    };

    return (
        <div>
            <p className="text-center choice-title mt-4">
                {strings.collection.pageTitles.registerChoice}
            </p>
        <div className='choice-container'>
            <Card onClick={goToUserRegister} className='choice-card text-dark bg-light mb-4'>
                {
                    <div className='card-img-top'>
                        <Card.Img variant='top' src={userRegister}/>
                    </div>
                }
                <Card.Body className='card-body'>
                    <h5 className='card-title' style={{fontWeight:"bold" }}>{title}</h5>
                    <p className='card-text'>{description}</p>
                </Card.Body>
            </Card>
            <Card onClick={goToRestRegister} className='choice-card text-dark bg-light mb-4'>
                {
                    <div className='card-img-top'>
                        <Card.Img variant='top' src={restRegister}/>
                    </div>
                }
                <Card.Body className='card-body'>
                    <h5 className='card-title' style={{fontWeight:"bold" }}>{title_rest}</h5>
                    <p className='card-text'>{description_rest}</p>

                </Card.Body>
            </Card>
        </div>
        </div>
    );
}