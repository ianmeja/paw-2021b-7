import paginatedResponse, { PaginatedData } from '../paginatedResponse';
import { BaseApiSlice } from '../baseApiSlice';
import { Restaurant, ListRestaurantParameters, CreateRestaurantParameters, UpdateRestaurantParameters } from './types';

const RestaurantApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({
        findRestaurant: build.query<Restaurant, string>({
            query: (url) => url.toString(),
            providesTags: (result) => (result ? [{ type: 'Restaurant', id: result.restId }] : ['Restaurant'])
        }),

        listRestaurants: build.query<PaginatedData<Restaurant[]>, ListRestaurantParameters>({
            query: (params) => `restaurants?${new URLSearchParams(Object.entries(params)).toString()}`,
            transformResponse: (response: Restaurant[], meta) => paginatedResponse(response, meta),
            providesTags: (result) =>
                result && result.data
                    ? [
                        ...result.data.map(({ restId }) => ({ type: 'Restaurant' as const, id: restId })),
                        { type: 'Restaurant', id: 'PARTIAL-LIST' }
                    ]
                    : [{ type: 'Restaurant', id: 'PARTIAL-LIST' }]
        }),

        listSearchRestaurants: build.query<Restaurant[], string>({
            query: (params) => `restaurants/${params}/search`
        }),

        createRestaurant: build.mutation<string|null, CreateRestaurantParameters>({
            query: (args) => {
                return {
                    url: 'restaurants',
                    method: 'POST',
                    body: args
                };
            },
            invalidatesTags: [{ type: 'Restaurant', id: 'PARTIAL-LIST' }]
        }),

        updateRestaurant: build.mutation<void, UpdateRestaurantParameters>({
            query: ({ id, ...args } ) => {
                return {
                    url: `restaurants/${id}`,
                    method: 'PUT',
                    body: args
                }
            },
            invalidatesTags: [{ type: 'Restaurant', id: 'PARTIAL-LIST' }]
        }),
    })
});

export const {
    useListRestaurantsQuery: useListRestaurants,
    useListSearchRestaurantsQuery: useListSearchRestaurants,
    useFindRestaurantQuery: useFindRestaurant,
    useCreateRestaurantMutation: useCreateRestaurant,
    useUpdateRestaurantMutation: useUpdateRestaurant
} = RestaurantApiSlice;
