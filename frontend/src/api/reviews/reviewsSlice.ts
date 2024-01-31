import { BaseApiSlice } from '../baseApiSlice';
import {addReviewParameters, Review, ListReviewsParameters, AnswerReviewParameters } from './types';

const ReviewApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({

        listReviews: build.query<Review[], ListReviewsParameters>({
            query: ({ rest_id}) => `restaurants/${rest_id}/reviews`,
        }),

        addReview: build.mutation<string|null, addReviewParameters>({
            query: ({rest_id,...args}) => {
                return {
                    url: `restaurants/${rest_id}/reviews`,
                    method: 'POST',
                    body: args
                };
            },
            invalidatesTags: [{ type: 'Review', id: 'PARTIAL-LIST' }]
        }),


        answerReview: build.mutation<string|null, AnswerReviewParameters>({
            query: ({ans_id,rest_id,...args}) => ({
                url: `restaurants/${rest_id}/reviews/${ans_id}`,
                method: 'PUT',
                body: args
            }),
            invalidatesTags: [{ type: 'Review', id: 'PARTIAL-LIST' }]
        }),


    })
});

export const {
    useListReviewsQuery: useListReviews,
    useAddReviewMutation: useAddReview,
    useAnswerReviewMutation: useAnswerReview,
} = ReviewApiSlice;