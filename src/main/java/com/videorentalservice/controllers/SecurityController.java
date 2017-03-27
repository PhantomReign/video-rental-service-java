package com.videorentalservice.controllers;

import com.videorentalservice.VRSException;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.User;
import com.videorentalservice.services.UserService;
import com.videorentalservice.services.common.EmailServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Rave on 21.03.2017.
 */
@Controller
public class SecurityController extends AbstractBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailServiceImplementation emailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private static String getURLWithContextPath(HttpServletRequest request)
    {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }


    @RequestMapping(value = "/forbidden", method = RequestMethod.GET)
    public String forbidden(){
        return "security/forbidden";
    }


    @RequestMapping(value="/forgotPassword", method= RequestMethod.GET)
    public String forgotPassword()
    {
        return "security/forgotPassword";
    }

    @RequestMapping(value="/forgotPassword", method=RequestMethod.POST)
    public String handleForgotPassword(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        String email = request.getParameter("email");

        User userByEmail = userService.getByEmail(email);
        if(userByEmail == null){
            redirectAttributes.addFlashAttribute("error", getMessage("error.email_not_found"));
            return "redirect:/forgotPassword";
        }

        try {
            String token = userService.resetPassword(email);
            String resetPasswordUrl = getURLWithContextPath(request)+"/resetPassword?email="+email+"&token="+token;
            this.sendForgotPasswordEmail(email, resetPasswordUrl);
            redirectAttributes.addFlashAttribute("info", getMessage("info.password_reset_link_sent"));
        } catch (VRSException e) {
            redirectAttributes.addFlashAttribute("info", e.getMessage());
        }
        return "redirect:/login";
    }

    private void sendForgotPasswordEmail(String email, String resetPasswordUrl) {
        try {

            // Prepare the evaluation context
            final Context ctx = new Context();
            ctx.setVariable("resetPasswordUrl", resetPasswordUrl);

            // Create the HTML body using Thymeleaf
            final String htmlContent = this.templateEngine.process("email/forgotPasswordEmail", ctx);

            emailService.sendEmail(email, getMessage("label.password_reset_email_subject"), htmlContent);
        } catch (VRSException ignored) {
        }
    }

    @RequestMapping(value="/resetPassword", method=RequestMethod.GET)
    public String resetPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes)
    {
        String email = request.getParameter("email");
        String token = request.getParameter("token");

        boolean valid = userService.verifyPasswordResetToken(email, token);
        if(valid){
            model.addAttribute("email", email);
            model.addAttribute("token", token);
            return "security/resetPassword";
        } else {
            redirectAttributes.addFlashAttribute("error", getMessage("error.invalid_password_reset_request"));
            return "redirect:/login";
        }

    }

    @RequestMapping(value="/resetPassword", method=RequestMethod.POST)
    public String handleResetPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes)
    {
        try
        {
            String email = request.getParameter("email");
            String token = request.getParameter("token");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            if(!password.equals(confirmPassword))
            {
                model.addAttribute("email", email);
                model.addAttribute("token", token);
                model.addAttribute("error", getMessage("error.password_conf_password_mismatch"));
                return "security/resetPassword";
            }
            String encodedPwd = passwordEncoder.encode(password);
            userService.updatePassword(email, token, encodedPwd);

            redirectAttributes.addFlashAttribute("info", getMessage("info.password_updated_success"));
        } catch (VRSException e) {
            redirectAttributes.addFlashAttribute("error", getMessage("error.invalid_password_reset_request"));
        }
        return "redirect:/login";
    }

}
