import {  Card, ListGroupItem } from 'react-bootstrap';
import { useAppDispatch } from '../hooks';
import { setLanguage } from '../api/i18n/i18nSlice';

export default function Footer() {
    const dispatch = useAppDispatch();

    function updateLanguage(language: string) {
        dispatch(setLanguage(language));
    }

    return (

            <Card.Footer
                className='d-flex justify-content-end align-content-center'
                style={{height: '40px'}}
            >
                <ListGroupItem bsPrefix=' '>
                    <button className="btn btn-link p-1" style={{fontSize: 15, color: "var(--color-primary)", paddingBottom: 0}} onClick={() => updateLanguage('en')}>
                        EN
                    </button>
                    <button className="btn btn-link p-1" style={{fontSize: 15, color: "var(--color-primary)", paddingBottom: 0}} onClick={() => updateLanguage('es')}>
                        ES
                    </button>
                </ListGroupItem>
            </Card.Footer>
    )
}
