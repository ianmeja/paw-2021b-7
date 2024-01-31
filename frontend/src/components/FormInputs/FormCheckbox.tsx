import { FormGroup, InputGroup, Form } from 'react-bootstrap';
import { UseFormRegister, Path } from 'react-hook-form';
import ValidationInterface from './ValidationInterface';
import React from 'react';

export default function FormCheckbox<T>(props: {
    register: UseFormRegister<T>;
    label: string;
    name: Path<T>;
    validation?: Partial<ValidationInterface<T>>;
    value?: number;
    checked?: boolean;
}) {
    const { register, label, name, value, validation, checked } = props;
    return (
        <label className="space-x-2 cursor-pointer">
            <FormGroup>
                <InputGroup>
                    <Form.Check className="label-check" style={{cursor: 'pointer'}} >
                        <Form.Check.Input id="checked" type="checkbox" value={value} {...register(name, validation)} defaultChecked={checked}/>
                        <Form.Check.Label htmlFor={"checked"}>{ label }</Form.Check.Label>
                    </Form.Check>
                </InputGroup>
            </FormGroup>
        </label>
    );
}