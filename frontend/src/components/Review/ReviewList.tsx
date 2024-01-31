import { Review} from '../../api/reviews/types';
import { strings } from '../../i18n/i18n';
import ReviewCard from './ReviewCard';
import React from "react";

function ReviewList(props: { reviews: Review[], userType: number }) {
    const { reviews,userType } = props;

    return (
        <div>
            {reviews.length === 0 ? (
                <p className='lead'>{strings.collection.noData.noReviews}</p>
            ) : (
                <div>
                    {reviews.map((review, i) => (
                        <div key={i}>
                            <ReviewCard review={review} userType={userType} />
                            <hr />
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default ReviewList;
