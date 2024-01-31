import { Button, Card, Form } from 'react-bootstrap';
import { strings } from '../../i18n/i18n';
import { useForm } from 'react-hook-form';
import FormInput from '../FormInputs/FormInputs';
import FormSelect from '../FormInputs/FormSelect';

interface FilterCardForm {
    name: string;
    cuisine: string;
    sort_by: string;
    max: number;
    min: number;
}

interface FilterCardProps {
    onSubmit: (data: FilterCardForm) => void;
    onClear: () => void;
    defaultValues: FilterCardForm;
}

function FilterCard(props: FilterCardProps) {

    const {
        register,
        handleSubmit,
        reset
    } = useForm<FilterCardForm>({
        defaultValues: {
            name: '',
            cuisine: undefined,
            sort_by: undefined,
            min: NaN,
            max: NaN,
        }
    });

    const {
        defaultValues: { name, cuisine, sort_by, min, max},
        onClear,
        onSubmit
    } = props;

    return (
        <>
            {(
                <Card className='card-style filters-card col-md-3 col-lg-3 col-12 mt-2 mb-2'>
                    <Card.Title className='w-100 d-flex align-items-center justify-content-between'>
                        <h4 className='color-spoon-black m-0'>{strings.collection.filter.title}</h4>
                        <Button
                            variant='link'
                            className='text-decoration-none p-0 m-0 text-primary'
                            onClick={() => {
                                onClear();
                                reset();
                            }}
                        >
                            {strings.collection.filterInfo.clear}
                        </Button>
                    </Card.Title>
                    <hr />

                    <Form onSubmit={handleSubmit(onSubmit)}>
                        <Card.Body style={{ padding: '0px' }}>
                            <FormInput
                                register={register}
                                type='text'
                                label={strings.collection.filter.search}
                                value={name}
                                name='name'
                            />
                            <FormSelect
                                register={register}
                                name='cuisine'
                                label={strings.collection.filter.cuisine}
                                value={cuisine ? cuisine.toString() : ''}
                                options={[['',strings.collection.filter.everyCuisine],["aleman","Aleman"],["americano","Americano"],["argentino","Argentino"],
                                    ["asiatico","Asiatico"],["autoctono","Autoctono"],["bar","Bar"],["bodegon","Bodegon"],["cerveceria","Cerveceria"],["chino","Chino"],
                                    ["de autor","De autor"],["de fusion","De fusion"],["español","Español"],["frances","Frances"],["hamburgueseria","Hamburgueseria"],["indio","Indio"],
                                    ["internacional","Internacional"],["israeli","Israeli"],["italiano","Italiano"],["japones","Japones"],["latino","Latino"],["meditarraneo","Meditarraneo"],
                                    ["mexicano","Mexicano"],["parrilla","Parrilla"],["peruano","Peruano"],["pescados y mariscos","Pescados y mariscos"],["picadas","Picadas"],["pizzeria","Pizzeria"],
                                    ["sin gluten","Sin gluten"],["sushi","Sushi"],["tapeo","Tapeo"],["vegano","Vegano"],["vegetariano","Vegetariano"],["venezolano","Venezolano"],
                                ]}
                            />
                            <FormSelect
                                register={register}
                                name='sort_by'
                                label={strings.collection.filter.orderBy}
                                value={sort_by}
                                options={[["rating","Rating"],["minPrice","Min. price"],["maxPrice","Max. price"]]}
                            />
                            <FormInput
                                register={register}
                                type='number'
                                label={strings.collection.filter.minPrice}
                                value={min ? min.toString() : ''}
                                name='min'
                                prependIcon='$'
                            />
                            <FormInput
                                register={register}
                                type='number'
                                label={strings.collection.filter.maxPrice}
                                value={max? max.toString() : ''}
                                name='max'
                                prependIcon='$'
                            />
                        </Card.Body>
                        <Button type='submit' className='w-100 mt-3 text-white'>
                            {strings.collection.filter.button}
                        </Button>
                    </Form>
                </Card>
            )}
        </>
    );
}

export default FilterCard;