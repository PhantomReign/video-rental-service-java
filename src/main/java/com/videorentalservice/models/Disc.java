package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rave on 18.02.2017.
 */
@Entity
@Table(name="discs")
public class Disc extends AbstractModelClass {

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="disc_genre",
            joinColumns={@JoinColumn(name="DISC_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="GENRE_ID", referencedColumnName="ID")})
    private List<Genre> genres = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy="discs")
    private List<Order> orders = new ArrayList<>();

    @NotBlank
    private String title;
    private String subTitle;
    @NotBlank
    private String originalTitle;
    private String originalSubTitle;
    @Size(max = 1000)
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String imageBGUrl;
    private String videoUrl;
    @NotNull
    private Integer year;
    @NotNull
    private BigDecimal price;
    @NotNull
    private BigInteger itemCount;

    // GENRES
    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    // ORDERS
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // Category
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    // AVAILABILITY
    public Boolean getAvailable() {
        return itemCount.intValue() > 0;
    }

    public BigInteger getItemCount() {
        return itemCount;
    }

    public void setItemCount(BigInteger itemCount) {
        this.itemCount = itemCount;
    }
}
