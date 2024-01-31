import { useRef } from 'react';
import { Button, FormGroup, InputGroup } from 'react-bootstrap';
import {useAddImage} from "../../api/images/imagesSlice";
import {strings} from "../../i18n/i18n";

export default function ImageInput<T>(props: { restId: number, onRefreshImg: any}) {

    const {restId, onRefreshImg} = props;

    const inputRef = useRef<HTMLInputElement>(null);

    const [addImage, resultAddImage] = useAddImage();

    async function addImageInput(file: File)  {
        console.log(file);
        await addImage({
            id: restId,
            image: file
        });
       onRefreshImg();
    }


    return (
        <FormGroup>
            <InputGroup>
                <Button
                    style={{ borderRadius: 0 }}
                    className="text-white"
                    onClick={() => inputRef.current?.click()}
                >
                    {strings.collection.restaurant.uploadImage} +
                </Button>
                <input
                    type='file'
                    className='d-none'
                    ref={inputRef}
                    onChange={(_) => {
                        if (inputRef?.current?.files && inputRef?.current?.files.length) {
                            addImageInput(inputRef.current.files[0])
                        }
                    }}
                />
            </InputGroup>
        </FormGroup>
    );
}