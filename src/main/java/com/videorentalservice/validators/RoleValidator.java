package com.videorentalservice.validators;

import com.videorentalservice.models.Role;
import com.videorentalservice.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Rave on 24.03.2017.
 */

@Component
public class RoleValidator implements Validator{
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RoleService roleService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return Role.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        Role role = (Role) target;
        String name = role.getName();
        Role RoleByName = roleService.getByName(name);
        if(RoleByName != null){
            errors.rejectValue(
                    "name",
                    "error.exists",
                    new Object[]{name},
                    "Rola "+ name +" už existuje");
        }
    }

}
