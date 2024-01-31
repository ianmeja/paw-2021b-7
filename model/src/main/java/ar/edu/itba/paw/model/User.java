package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(sequenceName = "users_userid_seq", name = "users_userid_seq", allocationSize = 1)
    private long userId;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String  password;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Favorite> favorites;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booker",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Booking> bookings;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private ConfirmationToken confirmationToken;

    User(){

    }

    public User(String username, String email, String password, boolean enabled) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public User(long userId, String username, String email, String password, boolean enabled){
        this(username, email,password, enabled);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
