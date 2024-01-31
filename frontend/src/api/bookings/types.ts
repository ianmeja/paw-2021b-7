export interface Booking {
    diners: number;
    date: Date;
    time: number;
    confirmed: boolean
    url?: string;
    booking_id: number;
    rest_id: number;
    user_id: number;
    booker: string;
    restaurant: string;
}

export interface addBookingParameters {
    diners: string;
    date: Date;
    time: string;
    rest_id: number;
}

export interface ListBookingsParameters {
    page?: number;
    limit?: number;
    id: number | null;

}
export interface DeleteBookingParameters {
    booking_id: number | null;
}

export interface ConfirmBookingParameters {
    booking_id: number | null;
}
