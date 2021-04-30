package com.activedgetechnologies.test.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/")
    public String index() {
        logger.info("redirect to => swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}
