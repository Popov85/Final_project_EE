package com.goit.popov.restaurant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    private static Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @GetMapping("/error403")
    public String showAuthorisationErrorPage() {
        return "th/error403";
    }

    @GetMapping("/error")
    public String showErrorPage(HttpServletRequest httpRequest, Model model) {
        String errorMsg = "";
        int httpErrorCode;
        httpErrorCode = getErrorCode(httpRequest);
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        model.addAttribute("explanation", errorMsg);
        return "th/error";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        Integer result = null;
        try {
            Object s = httpRequest.getAttribute("javax.servlet.error.status_code");
            LOGGER.info("ERROR CODE: "+s);
            if (s==null) return 0;
            result = (Integer) s;

        } catch (Exception e) {
            LOGGER.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
        }
        return result;
    }

}