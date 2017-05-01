package com.videorentalservice.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Rave on 30.04.2017.
 */
@Component
public class ImageValidator implements Validator {
    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        MultipartFile file = (MultipartFile) target;
        String nameInput;
        if (file.getName().equals("fileCover")){
            nameInput = "imageUrl";
        } else {
            nameInput = "imageBGUrl";
        }

        if(file.isEmpty() || file.getSize() == 0) {
            errors.rejectValue(nameInput, "error.choosefile");
        } else if (file.getSize() > 4500000 ){
            errors.rejectValue(nameInput, "error.exceedsize");

        } else {
            if(!(file.getContentType().toLowerCase().equals("image/jpg")
                    || file.getContentType().toLowerCase().equals("image/jpeg")
                    || file.getContentType().toLowerCase().equals("image/png"))){
                errors.rejectValue(nameInput, "error.unsupported");
            }
        }
    }
}
