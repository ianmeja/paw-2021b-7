package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.BookingService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import ar.edu.itba.paw.webapp.dto.input.RestaurantCreateDto;
import ar.edu.itba.paw.webapp.dto.input.RestaurantEditDto;
import ar.edu.itba.paw.webapp.dto.output.BookingDto;
import ar.edu.itba.paw.webapp.dto.output.RestaurantDto;
import ar.edu.itba.paw.webapp.exceptions.EmailAlreadyTakenException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.utils.PaginationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("restaurants")
@Component
public class RestaurantController {

    @Autowired
    private RestaurantService rs;

    @Autowired
    private BookingService bs;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Context
    private HttpServletRequest httpServletRequest;

    private final String defaultPageSize = "8";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list(@QueryParam("name") @DefaultValue("") String search,
                         @QueryParam("cuisine") String cuisine,
                         @QueryParam("neighborhood") String neighborhood,
                         @QueryParam("sort_by") String sortBy,
                         @QueryParam("min") @DefaultValue("0") String min,
                         @QueryParam("max") @DefaultValue("20000") String max,
                         @QueryParam("high_ratings") @DefaultValue("false") boolean highRatings,
                         @QueryParam("page") @DefaultValue("1") String page,
                         @QueryParam("page_size") @DefaultValue(defaultPageSize) String pageSize) {

        List<RestaurantDto> restaurantDtos;
        if (highRatings){
            restaurantDtos = rs.getAllHighRating(page, pageSize).stream().map(restaurant -> RestaurantDto.fromRestaurant(restaurant, uriInfo)).collect(Collectors.toList());
        }
        else {
            restaurantDtos = rs.search(search, cuisine, sortBy, min, max, page, pageSize).stream().map(restaurant -> RestaurantDto.fromRestaurant(restaurant, uriInfo)).collect(Collectors.toList());
        }

        if (restaurantDtos.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<RestaurantDto>>(restaurantDtos) {});
        PaginationUtils.setPaginationLinks(response, Integer.parseInt(page), Integer.parseInt(pageSize), rs.getCountRest(), uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results ", uriInfo.getPath(), page, restaurantDtos.size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRestaurant(@PathParam("id") long id) {
        final Restaurant restaurant = rs.findById(id).orElseThrow(RestaurantNotFoundException::new);

        LOGGER.info("GET /{}: Returning restaurant {}", uriInfo.getPath(), restaurant.getFullName());
        return Response.ok(RestaurantDto.fromRestaurant(restaurant, uriInfo)).build();
    }

    @POST
    @Produces(value = { MediaType.APPLICATION_JSON })
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response createRestaurant(@Valid RestaurantCreateDto restDto) throws EmailAlreadyTakenException {
        if (rs.findByEmail(restDto.getEmail()).isPresent()){
            throw new EmailAlreadyTakenException();
        }
        final Restaurant restaurant = rs.register(restDto.getEmail(), restDto.getPassword(), restDto.getPhone_number()
                , restDto.getFull_name(), restDto.getAddress(), restDto.getPrice(), restDto.getCuisine(), restDto.getNeighborhood()
                , restDto.getReservation(), restDto.getCapacity());

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(restaurant.getId())).build();

        LOGGER.info("POST /{}: Restaurant {} created", uriInfo.getPath(), restaurant.getFullName());
        return Response.created(uri)
                .header("Authorization", "Bearer " + jwtTokenUtil.generateAccessToken(restaurant.getEmail(), restaurant.getId(), 1))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON })
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response updateRestaurant(@PathParam("id") long id, @Valid RestaurantEditDto restDto) {
        rs.update(id, restDto.getPhone_number(), restDto.getFull_name(), restDto.getAddress(), restDto.getPrice(),
                restDto.getCuisine(), restDto.getNeighborhood(), restDto.getReservation(), restDto.getCapacity(),
                restDto.getAbout_me(), restDto.getMessage());

        LOGGER.info("PUT /{}: {} restaurant updated", uriInfo.getPath(), id);
        return Response.noContent().build();
    }


    @GET
    @Path("/{id}/bookings")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRestaurantBookings(@PathParam("id") long id){
        final Restaurant restaurant = rs.findById(id).orElseThrow(RestaurantNotFoundException::new);
        final List<BookingDto> bookings = bs.findBookByRestId(restaurant).stream().map(booking -> BookingDto.fromBooking(booking, uriInfo)).collect(Collectors.toList());

        if (bookings.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }
        return Response.ok(new GenericEntity<List<BookingDto>>(bookings) {}).build();
    }
}
