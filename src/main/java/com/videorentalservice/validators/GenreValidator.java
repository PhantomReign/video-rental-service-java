package com.videorentalservice.validators;

import com.videorentalservice.models.Genre;
import com.videorentalservice.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Rave on 25.03.2017.
 */
@Component
public class GenreValidator implements Validator {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private GenreService genreService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return Genre.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        Genre genre = (Genre) target;
        String name = genre.getName();
        Genre genreByName = genreService.getByName(name);
        if(genreByName != null){
            errors.rejectValue(
                    "name",
                    "error.exists",
                    new Object[]{name},
                    "Žáner "+ name +" už existuje");
        }
    }
}
