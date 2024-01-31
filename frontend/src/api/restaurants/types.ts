export interface Restaurant {
    description: string;
    about: string;
    price: number;
    cuisine: string;
    email: string;
    neighborhood: string;
    images?: File[];
    fullName: string;
    phoneNumber: string;
    address: string;
    message: string;
    capacity: number;
    reservation: boolean;
    imagesUrl?: string;
    restId: number;
    rating: number;
    cantReviews: number;
    url: string;
}

export interface ListRestaurantParameters {
    page?: number;
    limit?: number;
    page_size?: number;
    sort_by?: string;
    name?: string;
    max?: number;
    min?: number;
    cuisine?: string;
    neighborhood?: string;
    high_ratings?: boolean;
}

export interface CreateRestaurantParameters {
    price: number;
    cuisine: string;
    email: string;
    neighborhood: string;
    full_name: string;
    phone_number: string;
    password: string;
    address: string;
    reservation: boolean;
    capacity: number;
}

export interface UpdateRestaurantParameters {
    id: number;
    about: string;
    price: number;
    cuisine: string;
    phone_number: string;
    full_name: string;
    neighborhood: string;
    address: string;
    message: string;
    capacity: number;
    reservation: boolean;
}