export interface Favorite {
    rest_id: number;
    user_id: number;
    id: number;
    url?: string;
}

export interface ListFavoritesParameters {
    page?: number;
    page_size?: number;
    limit?: number;
    id: number | null;

}

export interface CheckFavoriteParameters {
    id: number | null;
    rest_id: number;
}

export interface AddFavoriteParameters {
    id: number;
    rest_id: number;

}

export interface DeleteFavoriteParameters {
    id: number;
    rest_id: number;
}