import { FormGroup, FormLabel, InputGroup, FormControl, Form } from 'react-bootstrap';
import { UseFormRegister, Path, FieldError } from 'react-hook-form';
import { ErrorMessageInterface } from '../../i18n/types';
import ValidationInterface from './ValidationInterface';

export default function FormSelect<T>(props: {
    register: UseFormRegister<T>;
    label: string;
    name: Path<T>;
    options: [string, string][];
    disabled?: boolean;
    value?: string;
    placeholder?: string;
    prependIcon?: JSX.Element | null;
    appendIcon?: JSX.Element | null;
    validation?: Partial<ValidationInterface<T>>;
    error?: FieldError | null;
    errorMessage?: ErrorMessageInterface;
}) {
    const {
        register,
        label,
        name,
        options,
        value,
        disabled,
        prependIcon,
        appendIcon,
        validation,
        error,
        errorMessage
    } = props;
    return (
        <FormGroup>
            <FormLabel>{label}</FormLabel>
            <InputGroup>
                {prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}
                <Form.Select
                    {...register(name, validation)}
                    isInvalid={error != null}
                    defaultValue={value}
                    disabled={disabled}
                >
                    {options.map(([value, message]) => (
                        <option key={value} value={value}>
                            {message}
                        </option>
                    ))}
                </Form.Select>
                {appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
                {errorMessage && error && (
                    <FormControl.Feedback type='invalid'>
                        {errorMessage[error.type as keyof ErrorMessageInterface]}
                    </FormControl.Feedback>
                )}
            </InputGroup>
        </FormGroup>
    );
}