package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private BigDecimal price;

    private BigDecimal total_days;

    @Column(nullable=false)
    private Date fromDate;

    @Column(nullable=false)
    private Date toDate;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    //@OneToMany(cascade=CascadeType.ALL, mappedBy="order")
    //private List<Disc> discs;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="order_disc",
            joinColumns={@JoinColumn(name="ORDER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="DISC_ID", referencedColumnName="ID")})
    private List<Disc> discs = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public int getTotalDiscs()
    {
        return discs.size();
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

    public List<Disc> getDiscs() {
        return discs;
    }

    public void setDiscs(List<Disc> discs) {
        this.discs = discs;
    }

    public String getFromDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(fromDate);
    }

    public void setFromDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date from = null;
        try {
            from = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.fromDate = from;
    }

    public String getToDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(toDate);
    }

    public void setToDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date to = null;
        try {
            to = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.toDate = to;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotal_days() {
        return total_days;
    }

    public void setTotal_days(BigDecimal total_days) {
        this.total_days = total_days;
    }
}
