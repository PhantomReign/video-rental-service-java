package com.videorentalservice.controllers.abstracts;

import com.videorentalservice.services.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Rave on 21.03.2017.
 */
public abstract class AbstractBaseController {

    @Autowired
    protected MessageSource messageSource;

    public String getMessage(String code)
    {
        return messageSource.getMessage(code, null, null);
    }

    public String getMessage(String code, String defaultMsg)
    {
        return messageSource.getMessage(code, null, defaultMsg, null);
    }

    @ModelAttribute("authenticatedUser")
    public AuthenticatedUser authenticatedUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser)
    {
        return authenticatedUser;
    }

    public static AuthenticatedUser getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            return ((AuthenticatedUser) principal);
        }
        return null;
    }

    public static boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

}
