import {Button, Col, Modal, Row} from 'react-bootstrap';
import { Review } from '../../api/reviews/types';
import Rating from './Rating';
import {strings} from "../../i18n/i18n";
import React, {useState} from "react";
import {userTypes} from "../../views/Restaurant";
import AnswerReviewForm from "../Forms/AnswerReviewForm";

function ReviewCard(props: { review: Review, userType: number}) {

    const {review, userType} = props;

    let rating = review.rating;
    let message = review.text;
    let createdAt = review.dateReview;
    let username = review.name;
    let answer = review.answer;

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => {
        if (userType === userTypes.notLogged) {
        } else setShow(true);
    };

    return (
        <>
            <div className="card" >
                <div className="card-header text-white d-flex justify-content-between" style={{height: '40px'}}>
                    <div className="col" style={{flexDirection:"column"}}>
                        <div className="row d-flex">
                            <div className="col-auto">   </div>
                            <div className="col-auto">
                                <h6> {username}  </h6>
                            </div>
                            <div className="col-auto">
                                <h6 className="date mt-0" style={{fontSize: 3}}>{createdAt}</h6>
                            </div>
                        </div>
                    </div>

                    <div className="col-2">
                        <div className="col-auto d-flex justify-content-end" style={{alignItems:"baseline", paddingRight: 5, paddingBottom:5}} >
                            <Rating rating={rating}/>
                        </div>
                    </div>
                </div>

                <div className="card-body">
                    <blockquote className="blockquote mb-0">
                        <div className="pt-1">
                            <p className='review_text'>{message}</p>
                            {(answer != undefined) &&
                                (<div className="card-body">
                                    <blockquote className="" style={{marginBottom: 0}}>
                                        <div>
                                            <Row>
                                                <Col className="col-auto">
                                                <h1 className='review_text fw-bold'> {strings.collection.reviews.answer}:  </h1>
                                                </Col>
                                                <Col>
                                                    <h5 style={{fontSize: 14}} className="mt-1">{answer} </h5>
                                                </Col>
                                            </Row>
                                        </div>
                                    </blockquote>
                                </div>)
                            }
                            { (answer == undefined) && (userType==1) &&
                                (<div className="col-auto d-flex justify-content-end">
                                    <Button onClick={handleShow}
                                         className="text-white">{strings.collection.restaurant.addAnswer}</Button>
                                </div>)
                            }
                        </div>
                    </blockquote>
                </div>
            </div>


            <Modal show={show} onHide={handleClose}>
                <Modal.Header className='bg-color-grey' closeButton>
                    <Modal.Title>{strings.collection.restaurant.addAnswer}</Modal.Title>
                </Modal.Header>
                <AnswerReviewForm restaurantId={review.rest_id} reviewId={review.id} setShow={setShow}/>
            </Modal>
        </>

    );
}

export default ReviewCard;
