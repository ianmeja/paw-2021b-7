package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_image_id_seq")
    @SequenceGenerator(sequenceName = "image_image_id_seq", name = "image_image_id_seq", allocationSize = 1)
    @Column(name = "image_id")
    private long imageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rest_id",nullable = false)
    private Restaurant rest;

    @Column(name = "data")
    private byte[] imageData;

    Image() {}

    public Image(long imageId, Restaurant rest, byte[] imageData) {
        this.imageId = imageId;
        this.rest = rest;
        this.imageData = imageData;
    }

    public Image(Restaurant rest, byte[] imageData) {
        super();
        this.rest = rest;
        this.imageData = imageData;
    }

    public Restaurant getRest() {
        return rest;
    }

    public void setRest(Restaurant rest) {
        this.rest = rest;
    }

    public long getImageId() {
        return imageId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public void setImageData(byte[] imageBlob) {
        this.imageData = imageBlob;
    }
}
