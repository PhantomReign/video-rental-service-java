package com.videorentalservice.controllers;

import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.User;
import com.videorentalservice.services.UserService;
import com.videorentalservice.validators.UserUpdateValidator;
import com.videorentalservice.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Rave on 27.03.2017.
 */
@Controller
public class UserCreateController extends AbstractBaseController {

    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserUpdateValidator userUpdateValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/register")
    public String registerNewUser(Model model){
        model.addAttribute("user", new User());
        return "security/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveNewUser(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes){

        userValidator.validate(user, result);
        userUpdateValidator.validate(user, result);
        if(result.hasErrors()){
            return "security/register";
        }

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        userService.register(user);
        redirectAttributes.addFlashAttribute("info", "Užívateľ bol úspešne vytvorený.");
        return "redirect:/login";
    }
}
