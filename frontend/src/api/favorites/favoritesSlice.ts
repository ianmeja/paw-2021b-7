import paginatedResponse, { PaginatedData } from '../paginatedResponse';
import { BaseApiSlice } from '../baseApiSlice';
import { Favorite, ListFavoritesParameters, AddFavoriteParameters, DeleteFavoriteParameters, CheckFavoriteParameters} from './types';
import {Restaurant} from "../restaurants/types";

const FavoriteApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({
       listFavorites: build.query<PaginatedData<Restaurant[]>, ListFavoritesParameters>({
           query: ({id,...params}) => `users/${id}/favorites?${new URLSearchParams(Object.entries(params)).toString()} `,
           transformResponse: (response: Restaurant[], meta) => paginatedResponse(response, meta),
           providesTags: (result) =>
               result && result.data
                   ? [
                       ...result.data.map(({ restId }) => ({ type: 'Favorite' as const, id: restId })),
                       { type: 'Favorite', id: 'PARTIAL-LIST' }
                   ]
                   : [{ type: 'Favorite', id: 'PARTIAL-LIST' }]
       }),

        checkFavorite: build.query<Favorite, CheckFavoriteParameters>({
            query: ({id,rest_id}) => `users/${id}/favorites/${rest_id}`,
        }),

        addFavorite: build.mutation<string|null, AddFavoriteParameters>({
            query: ({id,rest_id}) => ({
                url: `users/${id}/favorites/${rest_id}`,
                method: 'PUT',
            }),
            invalidatesTags: [{ type: 'Favorite', id: 'PARTIAL-LIST' }]
        }),

        deleteFavorite: build.mutation<string|null, DeleteFavoriteParameters>({
            query: ({id,rest_id}) => ({
                url: `users/${id}/favorites/${rest_id}`,
                method: 'DELETE',
            }),
            invalidatesTags: [{ type: 'Favorite', id: 'PARTIAL-LIST' }]
        })
    })
});

export const {
    useListFavoritesQuery: useListFavorites,
    useCheckFavoriteQuery: useCheckFavorite,
    useAddFavoriteMutation: useAddFavorite,
    useDeleteFavoriteMutation: useDeleteFavorite
} = FavoriteApiSlice;