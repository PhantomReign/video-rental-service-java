package com.videorentalservice.services;

import com.videorentalservice.models.Category;

/**
 * Created by Rave on 21.03.2017.
 */
public interface CategoryService extends JpaService<Category> {
    Category getByName(String name);
}
