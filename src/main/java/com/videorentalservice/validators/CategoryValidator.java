package com.videorentalservice.validators;

import com.videorentalservice.models.Category;
import com.videorentalservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Rave on 24.03.2017.
 */

@Component
public class CategoryValidator implements Validator {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return Category.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        Category category = (Category) target;
        String name = category.getName();
        Category categoryByName = categoryService.getByName(name);
        if(categoryByName != null){
            errors.rejectValue(
                    "name",
                    "error.exists",
                    new Object[]{name},
                    "Kategória "+ name +" už existuje");
        }
    }
}
