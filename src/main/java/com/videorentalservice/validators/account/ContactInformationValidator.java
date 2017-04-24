package com.videorentalservice.validators.account;

        import com.videorentalservice.models.User;
        import com.videorentalservice.services.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.MessageSource;
        import org.springframework.stereotype.Component;
        import org.springframework.validation.Errors;
        import org.springframework.validation.Validator;

/**
 * Created by Rave on 23.04.2017.
 */
@Component
public class ContactInformationValidator implements Validator {
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

        String phone = user.getPhone();

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
