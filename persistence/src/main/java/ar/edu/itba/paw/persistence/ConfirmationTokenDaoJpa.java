/* package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.model.ConfirmationToken;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ConfirmationTokenDaoJpa implements ConfirmationTokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        final TypedQuery<ConfirmationToken> query = em.createQuery("select t from ConfirmationToken t where t.id = :token", ConfirmationToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public ConfirmationToken saveToken(int user_id, LocalDateTime createdAt, String token) {

        final ConfirmationToken confirmationToken = new ConfirmationToken(user_id,token,createdAt,false);
        em.persist(confirmationToken);
        return confirmationToken;
    }

    @Override
    public int updateConfirmed(long token_id) {
        em.getTransaction().begin();
        int updatedCount = em.createQuery("update ConfirmationToken set confirmed = true where id = :token_id").executeUpdate();
        em.getTransaction().commit();
        em.close();
        return updatedCount;
    }

}*/

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.ConfirmationToken;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ConfirmationTokenDaoJpa implements ConfirmationTokenDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        final TypedQuery<ConfirmationToken> query = em.createQuery("from ConfirmationToken where token = :token", ConfirmationToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

    @Transactional
    @Override
    public ConfirmationToken saveToken(User user, LocalDateTime createdAt, String token) {
        final ConfirmationToken confirmationToken = new ConfirmationToken(user, token, createdAt, false);
        em.persist(confirmationToken);
        return confirmationToken;
    }

    @Transactional
    @Override
    public void updateConfirmed(long tokenId) {
        em.createQuery("update ConfirmationToken set confirmed = true where id = :tokenId").setParameter("tokenId",tokenId).executeUpdate();
    }
}
