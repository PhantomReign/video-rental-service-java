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
public class PasswordValidator implements Validator {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;

    private static boolean isBetween(int value, int min, int max)
    {
        return((value >= min) && (value <= max));
    }

    @Override
    public boolean supports(Class<?> clazz)
    {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String passwordToConfirm = user.getPasswordToConfirm();
        String confirmedPassword = user.getConfirmedPassword();

        if(!Objects.equals(passwordToConfirm, confirmedPassword)){
            errors.rejectValue("password",
                    "error.password_conf_password_mismatch");
        }

        if(Objects.equals(passwordToConfirm, "password")){
            errors.rejectValue("password",
                    "error.uniquepw");
        }
        if(Objects.equals(passwordToConfirm.trim(), "") || passwordToConfirm.isEmpty()){
            errors.rejectValue("password",
                    "error.emptypw");
        }

        if(!isBetween(passwordToConfirm.length(),8,60)){
            errors.rejectValue("password",
                    "error.sizepw");
        }
    }
}
