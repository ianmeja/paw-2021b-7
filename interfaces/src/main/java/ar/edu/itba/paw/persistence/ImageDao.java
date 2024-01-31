package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface ImageDao {
    Optional<Image> getImage(long imageId);

    Image uploadImage(Restaurant rest, byte[] imageData);

    void deleteImage(Image image);

    List<Image> getImagesByRestId(Restaurant rest);
}
