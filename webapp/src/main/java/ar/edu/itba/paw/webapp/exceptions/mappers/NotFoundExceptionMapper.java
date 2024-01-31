package ar.edu.itba.paw.webapp.exceptions.mappers;

import ar.edu.itba.paw.webapp.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN_TYPE).
                entity(e.getMessage()).build();
    }
}