package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 25.02.2017.
 */
@Entity
public class Booking extends AbstractModelClass {

    private String companyName;
    private int days;
    private BigDecimal totalCost;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="booking")
    @Fetch(FetchMode.SELECT)
    private List<User> users = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="booking")
    @Fetch(FetchMode.SELECT)
    private List<Disc> discs = new ArrayList<>();

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public List<Disc> getDiscs() {
        return discs;
    }

    public void setDiscs(List<Disc> discs) {
        this.discs = discs;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addDisc(Disc disc){
        if(!this.discs.contains(disc)){
            this.discs.add(disc);
        }

        if(disc.getBooking() != this){
            disc.setBooking(this);
        }
    }

    public void removeDisc(Disc disc){
        this.discs.remove(disc);
        disc.setBooking(null);
    }

    public void addUser(User user){
        if(!this.users.contains(user)){
            this.users.add(user);
        }

        if(user.getBooking() != this){
            user.setBooking(this);
        }
    }

    public void removeUser(User user){
        this.users.remove(user);
        user.setBooking(null);
    }
}
