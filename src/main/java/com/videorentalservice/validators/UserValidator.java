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
 * Created by Rave on 24.03.2017.
 */
@Component
public class UserValidator implements Validator {

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
        String email = user.getEmail();
        String userName = user.getUserName();
        String password = user.getPassword();

        User userByEmail = userService.getByEmail(email);
        User userByUserName = userService.getByUserName(userName);

        if(userByEmail != null){
            errors.rejectValue("email",
                    "error.exists",
                    new Object[]{email},
                    "Email "+email+" sa už používa");
        }

        if(Objects.equals(password, "password")){
            errors.rejectValue("password",
                    "error.uniquepw");
        }

        if (userByUserName != null) {
            errors.rejectValue("userName",
                    "error.exists",
                    new Object[]{userName},
                    "Používateľské meno "+userName+" sa už používa");
        }
    }
}
