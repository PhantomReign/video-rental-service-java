package com.videorentalservice.validators.account;

import com.videorentalservice.models.User;
import com.videorentalservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Created by Rave on 23.04.2017.
 */
@Component
public class PersonalInformationValidator implements Validator {
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
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String firstName = user.getFirstName();
        String lastName = user.getLastName();

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
    }
}
