package com.soulmate.Controller;

import com.soulmate.Entites.UserInfo;
import com.soulmate.Services.UserSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

   private  final UserSevice userSevice;

    public UserController(UserSevice userSevice) {
        this.userSevice = userSevice;
    }


    @GetMapping("/")
    public String homepage(){
        return "home";
    }
    @GetMapping("/form")
    public String loginpage(){
        return "form";
    }
    @GetMapping("/help")
    public  String help(){
        return "check";
    }
    @PostMapping("/save")
    public String registerUser(){
        return "home";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserInfo userInfo){
        userSevice.createUser(userInfo);
        return "home";
    }

}
