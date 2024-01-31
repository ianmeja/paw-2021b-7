package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.BookingService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.input.BookingCreateDto;
import ar.edu.itba.paw.webapp.exceptions.BookingNotAvailableException;
import ar.edu.itba.paw.webapp.exceptions.BookingNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;

@Path("bookings")
public class BookingController {

    @Autowired
    private UserService us;

    @Autowired
    private RestaurantService rs;

    @Autowired
    private BookingService bs;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Context
    private HttpServletRequest httpServletRequest;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    /*
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getBookings(@QueryParam("user_id") long userId,
                                @QueryParam("rest_id") long restId){
        final User user = us.findById(userId).orElse(null);
        if (user == null){
            final Restaurant restaurant = rs.findById(restId).orElseThrow(RestaurantNotFoundException::new);
            final List<BookingDto> bookings = bs.findBookByRestId(restaurant).stream().map(booking -> BookingDto.fromBooking(booking, uriInfo)).collect(Collectors.toList());

            if (bookings.isEmpty()) {
                LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
                return Response.noContent().build();
            }
            return Response.ok(new GenericEntity<List<BookingDto>>(bookings) {}).build();
        }
        final List<BookingDto> bookings = bs.findBookByUserId(user).stream().map(booking -> BookingDto.fromBooking(booking, uriInfo)).collect(Collectors.toList());

        if (bookings.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        return Response.ok(new GenericEntity<List<BookingDto>>(bookings) {}).build();
    }


    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getBooking(@PathParam("id") long id) throws BookingNotFoundException{

        final Optional<Booking> booking = bs.findBookById(id);
        if (!booking.isPresent()) {
            throw new BookingNotFoundException();
        }
        LOGGER.info("GET /{}: Returning booking {}  ", uriInfo.getPath(), id);
        return Response.ok().build();
    }
     */

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createBooking(@Valid BookingCreateDto bookingCreateDto) throws BookingNotAvailableException {
        final User user = ApiUtils.getLoggedUser(securityContext, us);
        final Restaurant restaurant = rs.findById(bookingCreateDto.getRest_id()).orElseThrow(RestaurantNotFoundException::new);

        final Booking booking = bs.addBook(restaurant, user, bookingCreateDto.getDiners(), bookingCreateDto.getDate(), bookingCreateDto.getTime());
        if (booking == null){
            throw new BookingNotAvailableException();
        }
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(booking.getBooking_id())).build();
        Response.ResponseBuilder responseBuilder = Response.created(uri);

        LOGGER.info("POST /{}: Booking {} in restaurant {} created", uriInfo.getPath(),booking.getBooking_id(), bookingCreateDto.getRest_id());
        return responseBuilder.build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBooking(@PathParam("id") long id) throws BookingNotFoundException{
        Booking booking = bs.findBookById(id).orElseThrow(BookingNotFoundException::new);
        if (booking.getBooker() != ApiUtils.getLoggedUser(securityContext, us) && booking.getRest() != ApiUtils.getLoggedRestaurant(securityContext, rs)){
            return Response.status(Response.Status.CONFLICT).build();
        }
        bs.deleteBooking(id);
        LOGGER.info("DELETE /{}: Booking {} deleted", uriInfo.getPath(), id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/confirm")
    public Response confirmBooking(@PathParam("id") long id){
        bs.updateConfirmed(id);
        LOGGER.info("PUT /{}: Booking {} confirmed", uriInfo.getPath(), id);
        return Response.noContent().build();
    }
}
