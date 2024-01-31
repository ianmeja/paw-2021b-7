package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.ConfirmationToken;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import ar.edu.itba.paw.webapp.dto.input.UserCreateDto;
import ar.edu.itba.paw.webapp.dto.input.UserEditDto;
import ar.edu.itba.paw.webapp.dto.output.BookingDto;
import ar.edu.itba.paw.webapp.dto.output.UserDto;
import ar.edu.itba.paw.webapp.exceptions.*;
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

@Path("users")
@Component
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    private ConfirmationTokenService cs;

    @Autowired
    private BookingService bs;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest httpServletRequest;

    @Context
    private SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") long id) {
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(UserDto.fromUser(user, uriInfo)).build();
    }

    @POST
    @Produces(value = { MediaType.APPLICATION_JSON })
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response createUser(@Valid UserCreateDto userDto) throws EmailAlreadyTakenException, UsernameAlreadyTakenException {
        if(us.findByEmail(userDto.getEmail()).isPresent()){
            throw new EmailAlreadyTakenException();
        }
        if (us.findByUsername(userDto.getUsername()).isPresent()){
            throw new UsernameAlreadyTakenException();
        }
        final User user = us.register(userDto.getUsername(), userDto.getEmail(), userDto.getPassword());

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build();
        Response.ResponseBuilder responseBuilder = Response.created(uri);

        LOGGER.info("POST /{}: User {} created", uriInfo.getPath(), user.getUsername());
        return responseBuilder.build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response updateUser(@PathParam("id") long id,
                               @Valid UserEditDto userEditDto) throws UsernameAlreadyTakenException {
        if (!us.findById(id).isPresent()){
            throw new UserNotFoundException();
        }
        if (us.update(id, userEditDto.getUsername()) == -1){
            throw new UsernameAlreadyTakenException();
        }
        LOGGER.info("PUT /{}: User {} updated", uriInfo.getPath(), userEditDto);
        return Response.noContent().build();
    }

    @PUT
    @Path("/verify")
    public Response verifyUser(@QueryParam("token") String token) throws InvalidTokenException, AlreadyVerifiedException {
        ConfirmationToken confirmationToken = cs.findByToken(token).orElseThrow(InvalidTokenException::new);
        User user = confirmationToken.getUser();
        if (confirmationToken.isConfirmed() && user.isEnabled()){
            throw new AlreadyVerifiedException();
        }
        try{
            cs.confirmToken(token);
        }catch(Exception e){
            throw new InvalidTokenException();
        }

        LOGGER.info("PUT /{}: User {} enabled", uriInfo.getPath(), user.getUsername());
        return Response.noContent()
                .header("Authorization", "Bearer " + jwtTokenUtil.generateAccessToken(user.getEmail(), user.getUserId(), 0))
                .build();
    }

    @GET
    @Path("/{id}/bookings")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserBookings(@PathParam("id") long id){
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        final List<BookingDto> bookings = bs.findBookByUserId(user).stream().map(booking -> BookingDto.fromBooking(booking, uriInfo)).collect(Collectors.toList());

        if (bookings.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        return Response.ok(new GenericEntity<List<BookingDto>>(bookings) {}).build();
    }
}

