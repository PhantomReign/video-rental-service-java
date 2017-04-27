package com.videorentalservice.controllers.error;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Rave on 28.04.2017.
 */

@Controller
public class UnknownErrorController implements ErrorController {

    @GetMapping("/error")
    public void redirectNonExistentUrlsToHome(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
