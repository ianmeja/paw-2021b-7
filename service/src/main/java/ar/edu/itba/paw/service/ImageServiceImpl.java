package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistence.ImageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    private static final int WIDTH = 351;
    private static final int HEIGHT = 262;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public Optional<Image> getImage(long imageId) {
        return imageDao.getImage(imageId);
    }

    private byte[] getScaledImage(byte[] image) throws IOException, InterruptedException {
        byte[] resizedImage;
        ByteArrayInputStream bais = new ByteArrayInputStream(image);

        try {
            java.awt.Image tmpImage = ImageIO.read(bais);
            java.awt.Image scaled = tmpImage.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_SMOOTH);

            // wait for image to be ready
            MediaTracker tracker = new MediaTracker(new java.awt.Container());
            tracker.addImage(scaled, 0);
            tracker.waitForAll();

            BufferedImage buffered = ((ToolkitImage) scaled).getBufferedImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffered, "png", baos);  // When put original format, colors are corrupted.
            baos.flush();
            resizedImage = baos.toByteArray();
            baos.close();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Image conversion failed. {}", e.getMessage());
            throw e;
        }
        return resizedImage;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Image> getImagesByRestId(Restaurant rest) {
        return imageDao.getImagesByRestId(rest);
    }

    @Transactional
    @Override
    public Image uploadImage(Restaurant rest, byte[] imageData) {
        byte[] resizedImage;
        try {
            resizedImage = getScaledImage(imageData);
        }catch(Exception e){
            return null;
        }
        return imageDao.uploadImage(rest, resizedImage);
    }


    @Transactional
    @Override
    public void deleteImage(Image image) {
        imageDao.deleteImage(image);
    }
}
