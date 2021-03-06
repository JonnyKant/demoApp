package com.modeling.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    String getAboutPage(Model model){
        model.addAttribute("page","about");
        return "main";
    }
}
