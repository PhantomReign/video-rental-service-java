package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Category;
import com.videorentalservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 21.03.2017.
 */
@Service
public class CategoryServiceImplementation implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getByName(String name) {
        return categoryRepository.getByName(name);
    }

    @Override
    public List<?> listAll() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Page<Category> findAll(Predicate predicate, Pageable pageable) {
        return categoryRepository.findAll(predicate, pageable);
    }

    @Override
    public Category getById(Integer id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public Category save(Category discObject) {
        return categoryRepository.save(discObject);
    }

    @Override
    public Category update(Category discObject) {
        return categoryRepository.save(discObject);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.delete(id);
    }


}
