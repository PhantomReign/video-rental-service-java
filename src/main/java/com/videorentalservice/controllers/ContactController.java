package com.videorentalservice.controllers;

import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Rave on 30.03.2017.
 */
@Controller
public class ContactController extends AbstractBaseController {

    @RequestMapping("/contact")
    String contact(){
        return "contact/contact";
    }
}