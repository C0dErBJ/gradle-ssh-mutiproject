package com.CD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * Created by C0dEr on 2016/12/3.
 */
@RequestMapping("login")
@Controller
public class WebLoginController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        ModelAndView mav = new ModelAndView("login");

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("a&p")) {
                    String deap = new String(Base64.getDecoder().decode(c.getValue()));
                    String[] ap = deap.split("\\|%\\|#\\|");
                    mav.addObject("account", ap.length >= 1 ? ap[0] : "");
                    mav.addObject("password", ap.length >= 2 ? ap[1] : "");
                    mav.addObject("rmb", ap.length >= 2);
                    break;
                }
            }
        }
        return mav;
    }

}
