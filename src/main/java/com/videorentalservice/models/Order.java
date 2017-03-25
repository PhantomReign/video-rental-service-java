package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rave on 25.02.2017.
 */
@Entity
@Table(name="orders")
public class Order extends AbstractModelClass {

    @Column(nullable=false)
    @NotEmpty
    private String status;

    @Column(nullable=false, unique=true)
    private String orderNumber;
    @Column(nullable=false)
    @NotEmpty
    private BigDecimal price;

    private BigDecimal penalty;

    @Column(nullable=false)
    @NotEmpty
    private Date fromDate;
    @Column(nullable=false)
    @NotEmpty
    private Date toDate;


    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;


    @OneToMany(cascade=CascadeType.ALL, mappedBy="order")
    private List<Disc> discs;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
    }

    public List<Disc> getDiscs() {
        return discs;
    }

    public void setDiscs(List<Disc> discs) {
        this.discs = discs;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
