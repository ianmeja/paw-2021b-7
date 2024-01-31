import { BaseApiSlice } from '../baseApiSlice';
import { Image, ListImagesParameters, AddImageParameters, DeleteImageParameters } from './types';

const ImagesApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({
        listImages: build.query<Image[], ListImagesParameters>({
            query: ({ id }) => `restaurants/${id}/images`,
        }),

        addImage: build.mutation<Image, AddImageParameters>({
            query: ({id, image}) => {
                let imageData = new FormData()
                imageData.append('image', image)
                console.log(imageData)
                return {
                    url: `restaurants/${id}/images`,
                    method: 'POST',
                    body: imageData
                };
            },
            invalidatesTags: [{ type: 'Image', id: 'PARTIAL-LIST' }]
        }),

        deleteImage: build.mutation<Image, DeleteImageParameters>({
            query: ({rest_id , image_id}) => ({
                url: `restaurants/${rest_id}/images/${image_id}`,
                method: 'DELETE',
            }),
            invalidatesTags: [{ type: 'Image', id: 'PARTIAL-LIST' }]
        })
    })
});

export const {
    useListImagesQuery: useListImages,
    useAddImageMutation: useAddImage,
    useDeleteImageMutation: useDeleteImage,
} = ImagesApiSlice;
