package ar.edu.itba.paw.webapp.config;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Locale;

@Provider
public class AcceptLanguageFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        List<Locale> acceptableLanguages = requestContext.getAcceptableLanguages();
        if (!acceptableLanguages.isEmpty()) {
            LocaleContextHolder.setLocale(acceptableLanguages.get(0));
        }
    }
}
