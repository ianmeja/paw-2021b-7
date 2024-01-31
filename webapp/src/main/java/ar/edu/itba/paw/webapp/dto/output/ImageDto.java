package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.model.Image;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ImageDto {
    private long image_id;
    private URI url;
    private byte[] image;

    public static ImageDto fromImage(Image image, UriInfo uri) {
        ImageDto imageDto = new ImageDto();
        imageDto.image_id = image.getImageId();
        imageDto.url = uri.getBaseUriBuilder().path("restaurants").path(String.valueOf(image.getRest().getId())).path("images").path(String.valueOf(image.getImageId())).build();
        return imageDto;
    }

    public long getImage_id() {
        return image_id;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }
}
