package com.CD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by C0dEr on 2016/12/3.
 */
@RequestMapping("")
@Controller
public class WebIndexController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("redirect:login");
    }

    @RequestMapping(value = "leftbody", method = RequestMethod.GET)
    public ModelAndView leftbody() {
        return new ModelAndView("leftbody");
    }

    @RequestMapping(value = "head", method = RequestMethod.GET)
    public ModelAndView head() {
        return new ModelAndView("head");
    }
}
