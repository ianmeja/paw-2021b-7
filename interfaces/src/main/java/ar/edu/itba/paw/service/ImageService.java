package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Optional<Image> getImage(long imageId);

    List<Image> getImagesByRestId(Restaurant rest);

    Image uploadImage(Restaurant rest, byte[] imageData);

    void deleteImage(Image image);
}
