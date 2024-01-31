import { ElementType } from 'react';
import { FormGroup, FormLabel, InputGroup, FormControl, Button } from 'react-bootstrap';
import { FieldError, Path, UseFormRegister } from 'react-hook-form';
import useErrorMessage from '../../hooks/useErrorMessage';
import { ErrorMessageInterface } from '../../i18n/types';
import ValidationInterface from './ValidationInterface';
import React from 'react';

export default function FormInput<T>(props: {
    register: UseFormRegister<T>;
    label: string;
    name: Path<T>;
    type: string;
    as?: ElementType;
    show?: boolean;
    disabled?: boolean;
    value?: string;
    error?: FieldError | null;
    errorMessage?: ErrorMessageInterface;
    placeholder?: string;
    prependIcon?: JSX.Element | string | null;
    appendIcon?: JSX.Element | null;
    appendIconOnClick?: Function;
    validation?: Partial<ValidationInterface<T>>;
}) {
    const {
        register,
        label,
        name,
        value,
        error,
        errorMessage,
        type,
        as,
        show,
        disabled,
        placeholder,
        prependIcon,
        appendIcon,
        appendIconOnClick,
        validation
    } = props;

    const errorDesc = useErrorMessage(errorMessage, error, validation);
    return (
        <FormGroup>
            <FormLabel>{label}</FormLabel>
            <InputGroup>
                {prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}
                <FormControl
                    as={as === undefined ? 'input' : as}
                    type={type === 'password' && show ? 'text' : type}
                    {...(type === 'date'
                            ? { min: new Date().toISOString().split('T')[0] }
                            : {})}
                    defaultValue={value}
                    placeholder={placeholder}
                    {...register(name, validation)}
                    isInvalid={error != null}
                    disabled={disabled}
                    md={50}
                    pattern="[^/><]+"
                />
                {errorDesc && <FormControl.Feedback type='invalid'>{errorDesc}</FormControl.Feedback>}
            </InputGroup>
        </FormGroup>
    );
}