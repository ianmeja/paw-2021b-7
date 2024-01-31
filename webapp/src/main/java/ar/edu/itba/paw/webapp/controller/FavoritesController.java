package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.output.RestaurantDto;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.utils.PaginationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;


@Path("users/{id}/favorites")
public class FavoritesController {

    @Autowired
    private UserService us;

    @Autowired
    private RestaurantService rs;

    @Context
    private SecurityContext securityContext;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest httpServletRequest;

    private final String defaultPageSize = "4";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserFavorites(@PathParam("id") long id,
                                     @QueryParam("page") @DefaultValue("1") int page,
                                     @QueryParam("page_size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);

        final List<RestaurantDto> favorites = us.findUserFavRest(user, page, pageSize).stream().map(restaurant -> RestaurantDto.fromRestaurant(restaurant, uriInfo)).collect(Collectors.toList());
        if (favorites.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<RestaurantDto>>(favorites) {});
        PaginationUtils.setPaginationLinks(response, page, pageSize, us.getCountFav(), uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results ", uriInfo.getPath(), page, favorites.size());
        return response.build();
    }

    @GET
    @Path("/{rest_id}")
    public Response checkUserFavorite(@PathParam("id") long id,
                                      @PathParam("rest_id") long restId) {
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        final Restaurant restaurant = rs.findById(restId).orElseThrow(RestaurantNotFoundException::new);
        final List<Restaurant> favorites = us.findUserFavRest(user, 1, us.getCountFav());
        if (favorites.isEmpty() || !favorites.contains(restaurant)) {
            LOGGER.info("GET /{}: Favorite restaurant {} not found.", uriInfo.getPath(), restaurant.getId());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        LOGGER.info("GET /{}: Favorite restaurant {} found. ", uriInfo.getPath(), restaurant.getId());
        return Response.noContent().build();
    }

    @PUT
    @Path("/{rest_id}")
    public Response addFavorite(@PathParam("id") long id,
                                @PathParam("rest_id") long restId) {
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        final Restaurant restaurant = rs.findById(restId).orElseThrow(RestaurantNotFoundException::new);
        us.addFav(user, restaurant);

        LOGGER.info("PUT /{}: Restaurant {} added to {} user favorites", uriInfo.getPath(), user.getUserId(), restaurant.getFullName());
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{rest_id}")
    public Response removeFavorite(@PathParam("id") long id,
                                   @PathParam("rest_id") long restId) {
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        final Restaurant restaurant = rs.findById(restId).orElseThrow(RestaurantNotFoundException::new);
        us.deleteFavorite(user, restaurant);

        LOGGER.info("DELETE /{}: Restaurant {} removed from favorites", uriInfo.getPath(), restaurant.getFullName());
        return Response.noContent().build();
    }

}
