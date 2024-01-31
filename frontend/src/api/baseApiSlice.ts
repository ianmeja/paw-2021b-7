import { RootState } from '../store';
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const BaseApiSlice = createApi({
    reducerPath: 'api',
    tagTypes: ['Restaurant', 'Review', 'Booking', 'Favorite','Menu','User','Image'],
    baseQuery: fetchBaseQuery({
        baseUrl: process.env.REACT_APP_BASE_URL || 'api',
        prepareHeaders(headers, { getState }) {
            const state = getState() as RootState;
            if (state && state.auth.token) headers.set('Authorization', 'Bearer ' + state.auth.token);
            headers.set('Accept-Language', state.i18n.lang);

            return headers;
        }
    }),
    endpoints: () => ({})
});