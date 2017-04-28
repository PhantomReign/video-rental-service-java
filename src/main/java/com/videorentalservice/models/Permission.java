package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Rave on 20.03.2017.
 */

@Entity
@Table(name="permissions")
public class Permission extends AbstractModelClass {

    @Column(nullable=false, unique=true)
    private String name;

    private String description;

    @ManyToMany(mappedBy="permissions", cascade= CascadeType.ALL)
    private List<Role> roles;

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

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }
}
