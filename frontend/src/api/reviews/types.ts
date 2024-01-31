export interface Review {
    text: string;
    rating: number;
    rest_id: number;
    dateReview: string;
    user_id: number;
    url?: string;
    id: number;
    name: string;
    answer: string;
}

export interface addReviewParameters {
    text: string;
    rating: number;
    rest_id: number;
}

export interface ListReviewsParameters {
    rest_id: number | null;

}

export interface AnswerReviewParameters {
    rest_id: number | null;
    ans_id: number;
    text: string;

}