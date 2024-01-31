package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.ConfirmationToken;
import ar.edu.itba.paw.model.User;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

public interface ConfirmationTokenService {

    Optional<ConfirmationToken> findByToken(String token);
    ConfirmationToken saveToken(User user, LocalDateTime createdAt, String token);
    void updateConfirmed(long token_id);
    String generateVerificationToken(User user);
    User confirmToken(String token);
}
