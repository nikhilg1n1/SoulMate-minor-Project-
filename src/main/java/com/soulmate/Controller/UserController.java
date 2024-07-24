package com.soulmate.Controller;

import com.soulmate.Entites.UserInfo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String homepage(){
        return "home";
    }


    @GetMapping("/form")
    public String loginPage(@ModelAttribute UserInfo userInfo){
        return "form";
    }
    @PostMapping("/save")
    public String registerUser(){
        return "home";
    }

}
