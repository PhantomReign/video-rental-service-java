package com.videorentalservice.validators;

import com.videorentalservice.models.User;
import com.videorentalservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Created by Rave on 01.05.2017.
 */
@Component
public class UserUpdateValidator implements Validator {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        User user = (User) target;

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phone = user.getPhone();

        if(! firstName.isEmpty()) {
            if (!firstName.toLowerCase().matches("[a-záéíĺóŕúýčďľňšťžäôůřěöüőű]+$")) {
                errors.rejectValue("firstName",
                        "error.invalid",
                        new Object[]{firstName},
                        "Meno " + firstName + " obsahuje nepovolené znaky");
            }
        }

        if(!lastName.isEmpty()) {
            if (!lastName.toLowerCase().matches("[a-záéíĺóŕúýčďľňšťžäôůřěöüőű]+$")) {
                errors.rejectValue("lastName",
                        "error.invalid",
                        new Object[]{lastName},
                        "Priezvisko "+lastName+" obsahuje nepovolené znaky");
            }
        }

        // Regexp on phone number:
        // Optional call prefix, it starts with '+' and can have 2 or 3 digits (Austria has +43) or '0' instead of it.
        // Then the number - 9 digits and they can be separated by space always after a group of 3 digits.
        if(!phone.isEmpty()) {
            if (!phone.matches("^((\\+[0-9]{2,3})? ?|[0]?)[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$")) {
                errors.rejectValue("phone",
                        "error.invalid",
                        new Object[]{phone},
                        "Telefónne číslo " + phone + " nie je v správnom formáte");
            }
        }
    }
}
