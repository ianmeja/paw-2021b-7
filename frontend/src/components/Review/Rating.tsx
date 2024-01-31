import { Rating as SimpleStarRating } from 'react-simple-star-rating';

function Rating(props: { rating: number }) {
    const { rating } = props;
    return (
        <div>
            {rating > 0 && (
                <div className='d-flex align-items-center mb-1'>
                    <SimpleStarRating readonly={true} ratingValue={rating * 20} size={20} fillColor='#ffe234' />
                </div>
            )}
        </div>
    );
}

export default Rating;