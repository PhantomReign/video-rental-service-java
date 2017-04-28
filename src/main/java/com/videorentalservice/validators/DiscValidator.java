package com.videorentalservice.validators;

import com.videorentalservice.models.Disc;
import com.videorentalservice.services.DiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.math.BigInteger;

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
        Integer year = disc.getYear();

        BigDecimal price = disc.getPrice();

        BigInteger itemCount = disc.getItemCount();


        if(year != null && year < 0){
            errors.rejectValue(
                    "year",
                    "error.isnegative",
                    new Object[]{year},
                    "Zadajte prosím nezáporný rok");
        }

        if (itemCount != null && itemCount.intValue() < 0){
            errors.rejectValue(
                    "itemCount",
                    "error.isnegative",
                    new Object[]{itemCount},
                    "Zadajte prosím nezáporný počet");
        }

        if(price != null && price.signum() == -1){
            errors.rejectValue(
                    "price",
                    "error.isnegative",
                    new Object[]{price},
                    "Zadajte prosím nezápornú cenu");
        }
    }
}

