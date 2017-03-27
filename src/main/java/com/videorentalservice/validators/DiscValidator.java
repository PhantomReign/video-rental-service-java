package com.videorentalservice.validators;

import com.videorentalservice.models.Disc;
import com.videorentalservice.services.DiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Ladislav on 28.03.2017.
 */
@Component
public class DiscValidator implements Validator {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private DiscService discService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return Disc.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        Disc disc = (Disc) target;
        String title = disc.getTitle();
        Disc discByTitle = discService.getByTitle(title);

        String originalTitle = disc.getOriginalTitle();
        Disc discByOriginalTitle = discService.getByOriginalTitle(originalTitle);

        if(discByTitle != null){
            errors.rejectValue(
                    "title",
                    "error.exists",
                    new Object[]{title},
                    "Disk s titulom "+ title +" už existuje");
        }

        if(discByOriginalTitle != null){
            errors.rejectValue(
                    "originalTitle",
                    "error.exists",
                    new Object[]{originalTitle},
                    "Disk s originálnym titulom "+ originalTitle +" už existuje");
        }
    }
}

