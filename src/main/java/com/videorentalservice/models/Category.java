package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 20.03.2017.
 */
@Entity
@Table(name="categories")
public class Category extends AbstractModelClass {

    @Column(nullable=false, unique=true)
    @NotBlank
    private String name;

    private String description;

    @OneToMany(mappedBy="category")
    private List<Disc> discs = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Disc> getDiscs() {
        return discs;
    }

    public void setDiscs(List<Disc> discs) {
        this.discs = discs;
    }
}
