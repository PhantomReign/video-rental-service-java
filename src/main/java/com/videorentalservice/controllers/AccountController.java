package com.videorentalservice.controllers;

import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.User;
import com.videorentalservice.services.UserService;
import com.videorentalservice.validators.account.ContactInformationValidator;
import com.videorentalservice.validators.account.PasswordValidator;
import com.videorentalservice.validators.account.PersonalInformationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Rave on 23.04.2017.
 */
@Controller
public class AccountController extends AbstractBaseController {
    private UserService userService;

    @Autowired
    private ContactInformationValidator contactInformationValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonalInformationValidator personalInformationValidator;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/account")
    public String accountShow(Model model,Principal principal){
        model.addAttribute("user", userService.getByUserName(principal.getName()));
        return "account/account-show";
    }

    @RequestMapping("/account/personal")
    public String accountEditPersonal(Model model,Principal principal){
        model.addAttribute("user", userService.getByUserName(principal.getName()));
        return "account/account-form-pi-update";
    }

    @RequestMapping("/account/password")
    public String accountEditPassword(Model model,Principal principal){
        model.addAttribute("user", userService.getByUserName(principal.getName()));
        return "account/account-form-pw-update";
    }

    @RequestMapping("/account/contact")
    public String accountEditContact(Model model,Principal principal){
        model.addAttribute("user", userService.getByUserName(principal.getName()));
        return "account/account-form-ci-update";
    }

    @RequestMapping(value = "/account/personal", method = RequestMethod.POST)
    public String accountPersonalInfo(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes){

        personalInformationValidator.validate(user, result);
        if(result.hasErrors()){
            return "account/account-form-pi-update";
        }
        userService.updateOnlyPersonalInfo(user);
        redirectAttributes.addFlashAttribute("info", "Zmena osobných údajov prebehla úspešne.");
        return "redirect:/account";
    }

    @RequestMapping(value = "/account/password", method = RequestMethod.POST)
    public String accountPassword(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes){

        passwordValidator.validate(user, result);
        if(result.hasErrors()){
            return "account/account-form-pw-update";
        }

        user.setPassword(passwordEncoder.encode(user.getConfirmedPassword()));
        userService.updateOnlyPassword(user);
        redirectAttributes.addFlashAttribute("info", "Zmena hesla prebehla úspešne.");
        return "redirect:/account";
    }

    @RequestMapping(value = "/account/contact", method = RequestMethod.POST)
    public String accountContactInfo(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes){

        contactInformationValidator.validate(user, result);
        if(result.hasErrors()){
            return "account/account-form-ci-update";
        }
        userService.updateOnlyContactInfo(user);
        redirectAttributes.addFlashAttribute("info", "Zmena kontaktných údajov prebehla úspešne.");
        return "redirect:/account";
    }

}
