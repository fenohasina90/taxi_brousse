package com.itu.taxi_brousse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping("")
    public ModelAndView initialisation(){
        return new ModelAndView("redirect:/voyages");
    }
}
