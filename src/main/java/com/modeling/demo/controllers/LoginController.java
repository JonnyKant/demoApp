package com.modeling.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model)
    {
        model.addAttribute("page", "login");
        return "main";
    }


    // Login form with error
//    @RequestMapping("/login-error")
//    public String loginError(Model model) {
//        model.addAttribute("page", "login");
//        model.addAttribute("loginError", true);
//        return "login";
//    }

}

