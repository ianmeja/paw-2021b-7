import { Row, Badge, Col } from 'react-bootstrap';
import { ListRestaurantParameters } from '../../api/restaurants/types';
import { strings } from '../../i18n/i18n';


function Filters(props: { filters: ListRestaurantParameters; removeSearchParam: Function }) {
    const { filters, removeSearchParam } = props;

    return (
        <>
            {
                <div>
                    <Row className='align-items-center justify-content-start my-2'>
                        {filters.name && filters.name.length > 0 && (
                            <div className='d-flex align-items-center'>
								<span className='me-2 align-middle'>
									{strings.collection.filterInfo.search}{' '}
                                    <Badge className='m-1 bg-color-spoon-light color-spoon-black'>
										{filters.name}
									</Badge>
								</span>
                            </div>
                        )}
                    </Row>
                    <Row className='align-items-center justify-content-start my-2'>
                        <Col md={7} lg={7} className='d-flex align-items-center'>
                            {(filters.cuisine) && (
                                <span className='me-2 align-middle'>
									{strings.collection.filterInfo.filtering}
                                    {filters.cuisine && (
                                        <Badge className='m-1 bg-color-spoon-light color-spoon-black'>
                                            {filters.cuisine}
                                        </Badge>
                                    )}
								</span>
                            )}
                        </Col>
                    </Row>
                    <Row className='align-items-center justify-content-start my-2'/>
                </div>
            }
        </>
    );
}

export default Filters;