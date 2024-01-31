package ar.edu.itba.paw.webapp.exceptions.mappers;

import ar.edu.itba.paw.webapp.dto.output.ErrorDto;
import ar.edu.itba.paw.webapp.exceptions.CustomRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Locale;

@Singleton
@Component
@Provider
public class CustomRuntimeExceptionMapper implements ExceptionMapper<CustomRuntimeException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRuntimeException.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(CustomRuntimeException exception) {
        LOGGER.error("{}: {} Status code: {}",
                exception.getClass().getName(),
                messageSource.getMessage(exception.getMessageCode(), null, Locale.ENGLISH),
                exception.getStatusCode());

        return Response
                .status(exception.getStatusCode())
                .entity(ErrorDto.fromErrorMsg(messageSource.getMessage(exception.getMessageCode(), null, LocaleContextHolder.getLocale())))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
