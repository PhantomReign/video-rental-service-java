package com.videorentalservice.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 19.02.2017.
 */
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    private String genre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<Disc> discs = new ArrayList<>();

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Disc> getDiscs() {
        return discs;
    }

    public void setDiscs(List<Disc> discs) {
        this.discs = discs;
    }

    public void addDisc(Disc disc){
        if(!this.discs.contains(disc)){
            this.discs.add(disc);
        }

        if(!disc.getGenres().contains(this)){
            disc.getGenres().add(this);
        }
    }

    public void removeDisc(Disc disc){
        this.discs.remove(disc);
        disc.getGenres().remove(this);
    }


}
