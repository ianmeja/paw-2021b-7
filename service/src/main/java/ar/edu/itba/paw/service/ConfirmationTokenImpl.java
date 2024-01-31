package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.ConfirmationToken;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.ConfirmationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationTokenImpl implements ConfirmationTokenService{

    @Autowired
    private ConfirmationTokenDao confirmationTokenDao;

    @Autowired
    private UserService us;

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        return confirmationTokenDao.findByToken(token);
    }

    @Transactional
    @Override
    public ConfirmationToken saveToken(User user, LocalDateTime createdAt, String token) {
        return confirmationTokenDao.saveToken(user, createdAt, token);
    }

    @Transactional
    @Override
    public void updateConfirmed(long token_id) {
        confirmationTokenDao.updateConfirmed(token_id);
    }

    @Override
    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        saveToken(user, LocalDateTime.now(), token);
        return token;
    }

    @Override
    @Transactional
    public User confirmToken(String token) {
        ConfirmationToken confirmationToken = findByToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if (confirmationToken.isConfirmed()) {
            throw new IllegalStateException("Email already confirmed");
        }

        updateConfirmed(confirmationToken.getId());
        User user = us.findById(confirmationToken.getUser_id()).orElseThrow(() ->
                new IllegalStateException("User not found"));

        us.enableUser(user.getEmail(), user.getUsername());
        return user;
    }
}
