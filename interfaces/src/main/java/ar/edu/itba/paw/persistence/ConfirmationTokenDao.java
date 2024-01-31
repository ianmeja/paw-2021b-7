package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.ConfirmationToken;
import ar.edu.itba.paw.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenDao {
    Optional<ConfirmationToken> findByToken(String token);
    ConfirmationToken saveToken(User user, LocalDateTime createdAt, String token);
    void updateConfirmed(long token_id);
}
