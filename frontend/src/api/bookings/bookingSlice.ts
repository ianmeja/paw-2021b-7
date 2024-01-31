import { BaseApiSlice } from '../baseApiSlice';
import {addBookingParameters, Booking, ListBookingsParameters, DeleteBookingParameters, ConfirmBookingParameters} from './types';

const BookingApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({
        listUserBookings: build.query<Booking[], ListBookingsParameters>({
            query: ({ id }) => `users/${id}/bookings`,
        }),

        listRestaurantBookings: build.query<Booking[], ListBookingsParameters>({
            query: ({ id }) => `restaurants/${id}/bookings`,
        }),

        addBooking: build.mutation<string|null, addBookingParameters>({
            query: (args) => {
                return {
                    url: 'bookings',
                    method: 'POST',
                    body: args
                };
            },
        }),
        deleteBooking: build.mutation<Booking, DeleteBookingParameters>({
            query: ({booking_id}) => ({
                url: `bookings/${booking_id}`,
                method: 'DELETE',
            }),
            invalidatesTags: [{ type: 'Booking', id: 'PARTIAL-LIST' }]
        }),
        confirmBooking: build.mutation<Booking, ConfirmBookingParameters>({
            query: ({booking_id}) => ({
                url: `bookings/${booking_id}/confirm`,
                method: 'PUT',
            }),
            invalidatesTags: [{ type: 'Booking', id: 'PARTIAL-LIST' }]
        })
    })
});

export const {
    useListUserBookingsQuery: useListUserBookings,
    useListRestaurantBookingsQuery: useListRestaurantBookings,
    useAddBookingMutation: useAddBooking,
    useConfirmBookingMutation: useConfirmBooking,
    useDeleteBookingMutation: useDeleteBooking
} = BookingApiSlice;