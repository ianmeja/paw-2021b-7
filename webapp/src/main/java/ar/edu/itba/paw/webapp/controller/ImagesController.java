package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.dto.output.ImageDto;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("restaurants/{id}/images")
@Component
public class ImagesController {

    @Autowired
    private RestaurantService rs;

    @Autowired
    private ImageService is;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest httpServletRequest;

    @Context
    Request request;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list(@PathParam("id") long restId) {
        final Restaurant restaurant = rs.findById(restId).orElseThrow(RestaurantNotFoundException::new);
        final List<ImageDto> images = is.getImagesByRestId(restaurant).stream().
                map(image -> ImageDto.fromImage(image, uriInfo)).collect(Collectors.toList());

        if (images.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<List<ImageDto>>(images) {}).build();
    }

    @GET
    @Path("/{image_id}")
    @Produces(value = {"image/webp"})
    public Response getImage(@PathParam("image_id") long id,
                             @Context javax.ws.rs.core.Request request){
        final Image img = is.getImage(id).orElseThrow(ImageNotFoundException::new);

        EntityTag eTag = new EntityTag(String.valueOf(id));

        Response.ResponseBuilder response = ApiUtils.getConditionalCacheResponse(request, eTag);

        if (response == null) {
            LOGGER.info("GET /{}: Returning image {}", uriInfo.getPath(), id);
            return Response.ok(img.getImageData()).tag(eTag).build();
        }

        LOGGER.info("GET /{}: Returning image {} not modified", uriInfo.getPath(), id);
        return response.build();
    }

    @POST
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    public Response addImage(@PathParam("id") long id,
                             @FormDataParam("image") byte[] image){
        final Restaurant restaurant = rs.findById(id).orElseThrow(RestaurantNotFoundException::new);
        if (image!=null){
            is.uploadImage(restaurant, image);
        }
        LOGGER.info("POST /{}: Image uploaded", uriInfo.getPath());
        return Response.created(uriInfo.getAbsolutePath()).build();
    }

    @DELETE
    @Path("/{image_id}")
    public Response deleteImage(@PathParam("image_id") long id) {
        final Image img = is.getImage(id).orElseThrow(ImageNotFoundException::new);
        is.deleteImage(img);

        LOGGER.info("DELETE /{}: Image {} deleted", uriInfo.getPath(), img.getImageId());
        return Response.noContent().build();
    }
}
