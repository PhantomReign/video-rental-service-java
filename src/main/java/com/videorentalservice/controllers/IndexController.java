package com.videorentalservice.controllers;

import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Rave on 16.02.2017.
 */
@Controller
public class IndexController extends AbstractBaseController {

    @RequestMapping("/")
    String index(){
        return "index";
    }
}
