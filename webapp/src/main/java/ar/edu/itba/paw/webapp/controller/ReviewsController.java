package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.ReviewService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.input.ReviewAnswerDto;
import ar.edu.itba.paw.webapp.dto.input.ReviewCreateDto;
import ar.edu.itba.paw.webapp.dto.output.ReviewDto;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("restaurants/{id}/reviews")
public class ReviewsController {

    @Autowired
    private UserService us;

    @Autowired
    private RestaurantService rs;

    @Autowired
    private ReviewService revs;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Context
    private HttpServletRequest httpServletRequest;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);


    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRestaurantReviews(@PathParam("id") long id){
        final Restaurant restaurant = rs.findById(id).orElseThrow(RestaurantNotFoundException::new);
        final List<ReviewDto> reviews = revs.getReviews(restaurant).stream().map(review -> ReviewDto.fromReview(review, uriInfo)).collect(Collectors.toList());

        if (reviews.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        return Response.ok(new GenericEntity<List<ReviewDto>>(reviews) {}).build();
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createReview(@PathParam("id") long restId,
                                 @Valid ReviewCreateDto reviewCreateDto){
        final User user = ApiUtils.getLoggedUser(securityContext, us);
        final Restaurant restaurant = rs.findById(restId).orElseThrow(RestaurantNotFoundException::new);

        final Review review = revs.addReview(user, restaurant, reviewCreateDto.getText(), reviewCreateDto.getRating());

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(review.getId())).build();
        Response.ResponseBuilder responseBuilder = Response.created(uri);

        LOGGER.info("POST /{}: Review {} of restaurant {} from user {} created", uriInfo.getPath(), review.getId(), restId, user.getUserId());
        return responseBuilder.build();
    }

    @PUT
    @Path("/{ans_id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createReviewAnswer(@PathParam("ans_id") long ansId,
                                       @Valid ReviewAnswerDto reviewAnswerDto){
        final Review review = revs.getReview(ansId).orElseThrow(ReviewNotFoundException::new);

        revs.addAnswerReview(review, reviewAnswerDto.getText());

        LOGGER.info("PUT /{}: Review {} answered", uriInfo.getPath(), review.getId());
        return Response.noContent().build();
    }

}
