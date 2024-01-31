import {Button, Col, Form, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import FormInput from "../FormInputs/FormInputs";
import {strings} from "../../i18n/i18n";
import FormSelect from "../FormInputs/FormSelect";
import {useAddMenu} from "../../api/menus/menusSlice";
import {useRef} from "react";

interface MenuFormT {
    dish: string;
    description: string;
    price: number;
    category: string;
}

export default function MenuForm(props: { restaurantId: number , onRefresh: any}) {
    let category = "Select";
    let disabled: undefined;
    const {
        register,
        handleSubmit,
        setValue,
        formState: {errors}
    } = useForm<MenuFormT>({
        defaultValues: {
            dish: '',
            description: '',
            price: 1,
            category: 'Select'
        }
    });

    const {restaurantId, onRefresh} = props;

    const [addMenu, result] = useAddMenu();
    const formMenu = useRef<any>();

    async function onSubmit(form: MenuFormT)  {
        await addMenu({
            rest_id: restaurantId,
            dish:  form.dish,
            description: form.description,
            price: form.price,
            category: form.category
        });
        onRefresh();
        if(formMenu.current != null){
            formMenu.current.reset();
        }
    }

    return (
        <Form ref={formMenu} onSubmit={handleSubmit(onSubmit)}>
            <Row className="ms-">
                <Col>
                    <FormInput
                        register={register}
                        type="text"
                        name="dish"
                        error={errors.dish}
                        errorMessage={strings.collection.menu.error.nameDish}
                        validation={{
                            required: true,
                            maxLength: 15,
                            minLength: 3
                        }}
                        label={strings.collection.menu.dishName}
                    />
                </Col>
                <Col className="col-lg-4">
                    <FormInput
                        register={register}
                        type="text"
                        name="description"
                        validation={{
                            required: false,
                            maxLength: 50,
                            minLength: 3
                        }}
                        error={errors.description}
                        errorMessage={strings.collection.menu.error.description}
                        label={strings.collection.menu.description}
                    />
                </Col>
                <Col className="col-lg-1">
                    <FormInput
                        register={register}
                        label={strings.collection.menu.price}
                        name='price'
                        value='1'
                        error={errors.price}
                        errorMessage={strings.collection.register.errors.price}
                        type='number'
                        validation={{
                            required: true,
                            min: 1
                        }}
                    />
                </Col>
                <Col className="col-lg-2">
                    <FormSelect
                        register={register}
                        name='category'
                        value={category}
                        disabled={disabled}
                        options={[["STARTER",strings.collection.menu.starter],["MAIN",strings.collection.menu.main],["DESSERT",strings.collection.menu.dessert]]}
                        label={strings.collection.register.cuisine}
                    />
                </Col>
                <Col>
                    <Button type="submit" className='ms-2 mt-4 btn text-white' variant='primary'>{strings.collection.menu.addDish}</Button>
                </Col>
            </Row>
        </Form>
    );
}