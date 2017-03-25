package com.videorentalservice.controllers;

import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Rave on 05.03.2017.
 */
@Controller
public class LoginController extends AbstractBaseController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "security/login";
    }
}
