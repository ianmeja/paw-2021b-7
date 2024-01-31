export interface Menu {
    dish: string;
    description: string;
    price: number;
    category: string;
    url?: string;
    dish_id: number;
}

export interface ListMenuParameters {
    id: number;
}

export interface AddMenuParameters {
    dish: string;
    description: string;
    price: number;
    category: string;
    rest_id: number;
}

export interface DeleteMenuParameters {
    menu_id: number;
    rest_id: number | null;
}