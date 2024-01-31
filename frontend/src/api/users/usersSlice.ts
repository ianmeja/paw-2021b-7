import { BaseApiSlice } from '../baseApiSlice';
import {User, CreateUserParameters, UpdateUserParameters, VerifyUserParameters} from './types';

const UsersApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({
        findUser: build.query<User, string>({
            query: (url) => url.toString()
        }),

        createUser: build.mutation<string|null, CreateUserParameters>({
            query: (args) => {
                return {
                    url: 'users',
                    method: 'POST',
                    body: args
                };
            },
            invalidatesTags: [{ type: 'User', id: 'PARTIAL-LIST' }]
        }),

        updateUser: build.mutation<void, UpdateUserParameters>({
            query: ({ id, ...args } ) => {
                return {
                    url: `users/${id}`,
                    method: 'PUT',
                    body: args
                };
            },
            invalidatesTags: [{ type: 'User', id: 'PARTIAL-LIST' }]
        }),
        
        verify: build.mutation<void, VerifyUserParameters>({
            query: ({ token }) => {
                return {
                    url: `/users/verify?token=${token}`,
                    method: 'PUT',
                };
            },
            transformResponse: (_, meta, { callback }) => {
                const auth = meta?.response?.headers.get('Authorization');
                if (auth == null) return callback(null);

                const token = auth.split(' ')[1];
                if (token == null) return callback(null);

                return callback(token);
            },
            invalidatesTags: [{ type: 'User', id: 'PARTIAL-LIST' }]
        })
    })
});

export const {
    useVerifyMutation: useVerify,
    useFindUserQuery: useFindUser,
    useCreateUserMutation: useCreateUser,
    useUpdateUserMutation: useUpdateUser,
} = UsersApiSlice;
