package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Restaurant;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class ImageDaoJpa implements ImageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Image> getImage(long imageId) {
        return Optional.ofNullable(em.find(Image.class, imageId));
    }

    @Override
    public Image uploadImage(Restaurant rest, byte[] imageData) {
        final Image image = new Image(rest, imageData);
        em.persist(image);
        return image;
    }

    @Override
    public List<Image> getImagesByRestId(Restaurant rest){
        final TypedQuery<Image> query = em.createQuery("select i from Image i where i.rest = :rest order by i.imageId asc", Image.class);
        query.setParameter("rest", rest);
        return query.getResultList();
    }

    @Override
    public void deleteImage(Image image) {
        em.remove(image);
    }
}
