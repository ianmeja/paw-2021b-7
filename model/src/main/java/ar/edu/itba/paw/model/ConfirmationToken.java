package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokens_tokenId_seq")
    @SequenceGenerator(sequenceName = "tokens_tokenId_seq", name = "tokens_tokenId_seq", allocationSize = 1)
    @Column(name = "tokenId")
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(length = 100, nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean confirmed;

    ConfirmationToken(){}

    public ConfirmationToken(User user, String token, LocalDateTime createdAt, boolean confirmed) {
        super();
        this.token = token;
        this.createdAt = createdAt;
        this.user = user;
        this.confirmed = confirmed;
    }

    public ConfirmationToken(long token_id, User user, String token, LocalDateTime createdAt, boolean confirmed) {
        this(user, token, createdAt, confirmed);
        this.id = token_id;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user.getUserId();
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public User getUser() {
        return user;
    }
}
