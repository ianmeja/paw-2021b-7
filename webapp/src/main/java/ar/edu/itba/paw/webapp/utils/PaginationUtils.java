package ar.edu.itba.paw.webapp.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PaginationUtils {

    private PaginationUtils() {
        throw new AssertionError();
    }

    public static <T> void setPaginationLinks(Response.ResponseBuilder response, int page, int pageSize, int totalCount, UriInfo uriInfo) {
        final int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        if (page < totalPages) {
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page + 1).build().toString(), "next");
        }
        if (page > 1) {
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page - 1).build().toString(), "prev");
        }
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", totalPages).build().toString(), "last");
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", 1).build().toString(), "first");
        response.header("Total-Elements", totalCount);
    }
}
