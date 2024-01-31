package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_id_seq")
    @SequenceGenerator(sequenceName = "reviews_id_seq", name = "reviews_id_seq", allocationSize = 1)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable = false)
    private User reviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rest_id",nullable = false)
    private Restaurant rest; //mapea por la primary key de restaurant

    @Column(nullable = false, length = 100)
    private String text;

    @Column
    private int rating;

    @Column(name="date_review")
    private String dateReview;

    private String name;

    @Column(length = 100)
    private String answer;

    Review (){

    }

    public Review(User reviewer, Restaurant rest, String text, int rating, String dateReview, String answer) {
        super();
        this.reviewer = reviewer;
        this.rest = rest;
        this.text = text;
        this.rating = rating;
        this.dateReview = dateReview;
        this.answer = answer;
    }

    public Review(long id, User reviewer, Restaurant rest, String text, int rating, String dateReview, String answer) {
        this.id = id;
        this.reviewer = reviewer;
        this.rest = rest;
        this.text = text;
        this.rating = rating;
        this.dateReview = dateReview;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public long getUser_id() {
        return reviewer.getUserId();
    }

    public long getRest_id() {
        return rest.getId();
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public String getDateReview() {
        return dateReview;
    }

    public void setName(String username) {
        this.name=username;
    }

    public String getName() {
        return name;
    }

    public String getAnswer() {
        return answer;
    }

    public Restaurant getRest() {
        return rest;
    }

    public User getReviewer(){ return reviewer; }

    public void setRest(Restaurant rest) {
        this.rest = rest;
    }
}
