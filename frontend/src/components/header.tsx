import { Col, Container, Image, Nav, Navbar, NavDropdown, Row} from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';
import logo from '../assets/images/logo2.png';
import {  BsPersonFill } from 'react-icons/bs';
import {strings} from "../i18n/i18n";
import { useNavigate } from 'react-router';
import { setCredentials } from '../api/auth/authSlice';
import React from 'react';
import useUserId from "../hooks/useUserId";
import { useAppDispatch } from '../hooks';


function LoggedUserInNavBar(props: { dispatch: Function }) {
    const {dispatch } = props;

    const navigate = useNavigate();

    const logout = () => {
        navigate('/');
        dispatch(setCredentials({ token: null, rememberMe: false }));
    };


    return (
        <NavDropdown title={<BsPersonFill className='me-1 text-white' />} >
            <NavDropdown.Item onClick={() => navigate('profile')}>
                <span>{strings.collection.header.profile}</span>
            </NavDropdown.Item>
            <NavDropdown.Item onClick={logout}>
                <span>{strings.collection.header.logout}</span>
            </NavDropdown.Item>
        </NavDropdown>
    );
}

function LoggedRestInNavBar(props: { dispatch: Function }) {
    const {dispatch } = props;
    const navigate = useNavigate();
    let userId = useUserId();

    if(!userId){
        userId = [0,0];
    }

    const logout = () => {
        navigate('/');
        dispatch(setCredentials({ token: null, rememberMe: false }));
    };

    return (
        <>
        <LinkContainer to={`/restaurants/${userId[0]}`}>
            <Nav.Link as='a' className='active nav-bar-link'>
                {strings.collection.header.myRestaurant}
            </Nav.Link>
        </LinkContainer>
        <NavDropdown title={<BsPersonFill className='me-1 text-white' />} >
            <NavDropdown.Item onClick={() => navigate('rest-profile')}>
                <span>{strings.collection.header.profile}</span>
            </NavDropdown.Item>
            <NavDropdown.Item onClick={logout}>
                <span>{strings.collection.header.logout}</span>
            </NavDropdown.Item>
        </NavDropdown>
        </>
    );
}

function LoggedOutNavBar() {
    return (
        <React.Fragment>
            <LinkContainer to='/login'>
                <Nav.Link as='a' className='active'>
                    {strings.collection.header.login}
                </Nav.Link>
            </LinkContainer>

            <LinkContainer to='/register'>
                <Nav.Link as='a' className='active'>
                    {strings.collection.header.signup}
                </Nav.Link>
            </LinkContainer>
        </React.Fragment>
    );
}

export default function Header() {
    //null no loggeado 1 rest 0 usuario

    const dispatch = useAppDispatch();
    let user = useUserId();
    let id;
    let type;

    if(user){
        id = user[0];
        type = user[1];
    }

    return (
        <Navbar collapseOnSelect expand='lg' bg='primary' variant='dark' className='nav-bar-style'>
            <Container>
                <LinkContainer to='/'>
                    <Navbar.Brand>
                        <Row className='row-navbar'>
                            <Col>
                                <Image alt='spoon' src={logo} height='50px' />
                            </Col>
                            <Col>
                                <h3 className='text-white mt-2'>Spoon</h3>
                            </Col>
                        </Row>
                    </Navbar.Brand>
                </LinkContainer>
                <Navbar.Toggle aria-controls='responsive-navbar-nav' />
                <Navbar.Collapse className='mt-2' id='responsive-nav-bar'>
                    <Nav className='ms-auto d-flex align-items-center'>
                        <LinkContainer to='/explore'>
                            <Nav.Link as='a' className='active nav-bar-link'>
                                {strings.collection.header.explore}
                            </Nav.Link>
                        </LinkContainer>
                        {!id && <LoggedOutNavBar/>}
                        {type==1 &&
                        <LoggedRestInNavBar dispatch={dispatch}/>
                        }
                        {type==0 &&
                        <LoggedUserInNavBar dispatch={dispatch}/>
                        }
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}
