package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rave on 18.02.2017.
 */
@Entity
public class Disc extends AbstractModelClass {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<Genre> genres = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, targetEntity=Booking.class)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String title;
    private String subTitle;
    private String originalTitle;
    private String originalSubTitle;
    @Size(max = 1000)
    private String description;
    private String type;
    private String imageUrl;
    private String imageBGUrl;
    private String videoUrl;
    private Integer year;
    private BigDecimal price;

    // GENRES
    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void addGenre(Genre genre){
        if(!this.genres.contains(genre)){
            this.genres.add(genre);
        }

        if(!genre.getDiscs().contains(this)){
            genre.getDiscs().add(this);
        }
    }

    public void removeGenre(Genre genre){
        this.genres.remove(genre);
        genre.getDiscs().remove(this);
    }

    // BOOKING
    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    // TITLE
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // SUBTITLE
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    // ORIG. TITLE
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    // ORIG. SUBTITLE
    public String getOriginalSubTitle() {
        return originalSubTitle;
    }

    public void setOriginalSubTitle(String originalSubTitle) {
        this.originalSubTitle = originalSubTitle;
    }

    // DESCRIPTION
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // TYPE
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // IMG URL
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // IMG BG URL
    public String getImageBGUrl() {
        return imageBGUrl;
    }

    public void setImageBGUrl(String imageBGUrl) {
        this.imageBGUrl = imageBGUrl;
    }

    // VIDEO URL
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    // YEAR
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    // PRICE
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
