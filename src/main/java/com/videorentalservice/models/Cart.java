package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Rave on 18.02.2017.
 */
public class Cart extends AbstractModelClass {

    private List<Disc> items;

    public Cart(){
        if (items == null)
            items = new ArrayList<>();
    }

    public List<Disc> getItems() {
        return items;
    }

    public Boolean addItem(Disc disc)
    {
        Boolean add = true;
        for (Disc tmp :items) {
            if (tmp.getId() == disc.getId())
                add = false;
        }

        if (add)
        {
            items.add(disc);
            return true;
        }
        return false;
    }

    public void removeItem(Disc disc)
    {
        items.removeIf(tmp -> tmp.getId() == disc.getId());
    }

}
