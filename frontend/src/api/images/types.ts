export interface Image {
    image_id: number;
    data: string | Blob;
    rest_id: number;
    url: string;
}

export interface ListImagesParameters {
    id: number | null;
}

export interface AddImageParameters {
    id: number | null;
    image: string | Blob;
}

export interface DeleteImageParameters {
    rest_id: number | null;
    image_id: number | null;
}
