package com.activedgetechnologies.test.api;


import com.activedgetechnologies.test.exception.ResourceNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Profile("production")
@RestController
public class BaseController {

    @RequestMapping(value = "swagger-ui.html", method = RequestMethod.GET)
    public void getSwagger(HttpServletResponse httpResponse) throws ResourceNotFoundException {
        throw new ResourceNotFoundException("Please enter a valid path");
    }
}