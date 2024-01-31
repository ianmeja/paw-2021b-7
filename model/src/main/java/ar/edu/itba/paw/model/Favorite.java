package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorites_id_seq")
    @SequenceGenerator(sequenceName = "favorites_id_seq", name = "favorites_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rest_id",nullable = false)
    private Restaurant rest;

    Favorite(){}

    public Favorite(long id, User user, Restaurant rest) {
        this.id = id;
        this.user = user;
        this.rest = rest;
    }

    public Favorite(User user, Restaurant rest) {
        super();
        this.user = user;
        this.rest = rest;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRest() {
        return rest;
    }
}
