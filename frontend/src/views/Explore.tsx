import { useListRestaurants } from '../api/restaurants/restaurantsSlice';
import usePaginatedResponse from '../hooks/usePaginatedResponse';
import { ListRestaurantParameters } from '../api/restaurants/types';
import RestaurantCardList from '../components/Restaurant/RestaurantCardList';
import FilterCard from '../components/Explore/FilterCard';
import { Col, Container, Row } from 'react-bootstrap';
import { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { strings } from '../i18n/i18n';
import NoDataCard from '../components/NoDataCard';
import { URLSearchParamsInit, useSearchParams } from 'react-router-dom';
import { useEffect } from 'react';
import Filters from '../components/Explore/Filters';
import PagesList from "../components/PagesList";

function serialize<T>(data: T): URLSearchParamsInit {
    return Object.entries(data);
}

function deserialize<T extends Object>(searchParams: URLSearchParams): T {
    return Array.from(searchParams.entries()).reduce(
        (acc, el) => ({
            [el[0]]: el[1],
            ...acc
        }),
        {} as T
    );
}

function processFilters(filters: ListRestaurantParameters) {
    let newFilters = { ...filters };
    if (isNaN(filters.min || NaN)) delete newFilters.min;
    if (isNaN(filters.max || NaN)) delete newFilters.max;
    return newFilters;
}

function useProcessedFilters(unprocessed: ListRestaurantParameters) {
    const [filters, setFilters] = useState(processFilters(unprocessed));
    useEffect(() => {
        setFilters(processFilters(unprocessed));
    }, [unprocessed]);
    return filters;
}

function Explore() {
    const [searchParams, setSearchParams] = useSearchParams();

    const [page, setPage] = useState(1);
    const [filters, setFilters] = useState<ListRestaurantParameters>(deserialize(searchParams));

    const processedFilters = useProcessedFilters(filters);

    useEffect(() => {
        setSearchParams(serialize(processedFilters));
    }, [filters, setSearchParams, processedFilters]);

    function removeSearchParam(name: keyof ListRestaurantParameters) {
        setFilters((prev) => {
            let copy = { ...prev };
            delete copy[name];
            return copy;
        });
    }

    const { data, pages } = usePaginatedResponse(
         useListRestaurants({
            page: page,
            ...processedFilters
         })
     );

    return (
        <>
            <Helmet>
                <title>{strings.collection.pageTitles.explore}</title>
            </Helmet>
            <Container>
                <Row className='align-items-start justify-content-center'>
                    <Col md={3} lg={3}>

                        <FilterCard
                           defaultValues={deserialize(searchParams)}
                           onClear={() => setFilters({})}
                           onSubmit= {(data) => {setFilters(data); console.log(data)}}
                        />
                    </Col>
                    <Col md={9} lg={9}>
                        <Filters filters={filters} removeSearchParam={removeSearchParam} />
                        {data === null || (data && data.length === 0) ? (
                            <NoDataCard
                                title={strings.collection.noData.restaurantNotFoundTitle}
                            />
                        ) : (
                            data && (
                                <>
                                    <RestaurantCardList restaurants={data} restaurantsPerRow={4} />
                                    <PagesList pages={pages} page={page} setPage={setPage} />
                                </>
                            )
                        )}
                    </Col>
                </Row>
            </Container>
        </>
    );
}

export default Explore;