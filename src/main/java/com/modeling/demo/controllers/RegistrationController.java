package com.modeling.demo.controllers;

import com.modeling.demo.dto.UserDto;
import com.modeling.demo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class RegistrationController {

        @Autowired
        MyUserDetailsService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        model.addAttribute("page","registration");
        return "main";
    }

    @PostMapping("/registration")
    public String registerUserAccount(
            @ModelAttribute("userDto") @Valid UserDto userDto, BindingResult result, Model model) {
        if(!result.hasErrors()){
        try {
                userService.registerNewUserAccount(userDto);
        } catch (Exception uaeEx) {
            model.addAttribute("page","registration");
            model.addAttribute("exists", "exists");
            return "main";
        }
        model.addAttribute("page","login");
        return "main";
        }
        model.addAttribute("page","registration");
        model.addAttribute("result", result);
        return "main";
    }

}
