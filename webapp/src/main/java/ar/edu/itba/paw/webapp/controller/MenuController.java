package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Menu;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.MenuService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.dto.input.MenuCreateDto;
import ar.edu.itba.paw.webapp.dto.output.MenuDto;
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
import java.util.List;
import java.util.stream.Collectors;

@Path("restaurants/{id}/menu")
public class MenuController {

    @Autowired
    private RestaurantService rs;

    @Autowired
    private MenuService ms;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest httpServletRequest;

    @Context
    private SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRestaurantMenu(@PathParam("id") long restId) {
        if (!rs.findById(restId).isPresent()){
            throw new RestaurantNotFoundException();
        }
        final List<MenuDto> menuDtoList = ms.findMenuByRestId(restId).stream().map(menu -> MenuDto.fromMenu(menu, uriInfo)).collect(Collectors.toList());

        if (menuDtoList.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        LOGGER.info("GET /{}: Returning {} restaurant menu ", uriInfo.getPath(), restId);
        return Response.ok(new GenericEntity<List<MenuDto>>(menuDtoList) {}).build();
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response addMenu(@Valid MenuCreateDto menuCreateDto) {
        final Restaurant restaurant = ApiUtils.getLoggedRestaurant(securityContext, rs);
        final Menu menu = ms.addMenu(restaurant, menuCreateDto.getDish(), menuCreateDto.getDescription(), menuCreateDto.getPrice(), menuCreateDto.getCategory());

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(menu.getDish_id())).build();
        Response.ResponseBuilder responseBuilder = Response.created(uri);

        LOGGER.info("POST /{}: Restaurant {} added to favorites", uriInfo.getPath(), restaurant.getFullName());
        return responseBuilder.build();
    }

    @DELETE
    @Path("/{menu_id}")
    public Response removeMenu(@PathParam("menu_id") long menuId) {
        ms.deleteMenu(menuId);
        LOGGER.info("DELETE /{}: Dish {} removed from menu", uriInfo.getPath(), menuId);
        return Response.noContent().build();
    }
}
