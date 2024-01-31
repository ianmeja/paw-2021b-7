import { Card } from 'react-bootstrap';

function NoDataCard(props: { title: string}) {
    const { title } = props;

    return (
        <Card className='card-style d-flex justify-content-center align-items-center mt-2'>
            <h3>{title}</h3>
        </Card>
    );
}

export default NoDataCard;
