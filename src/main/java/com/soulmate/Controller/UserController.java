package com.soulmate.Controller;

import com.soulmate.Entites.UserInfo;
import com.soulmate.Exceptions.UserAlreadyExistsException;
import com.soulmate.Services.CustomUserService;
import com.soulmate.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

   private  final UserService userService;
   private final CustomUserService customUserService;
    public UserController(UserService userService, CustomUserService customUserService) {
        this.userService = userService;
        this.customUserService = customUserService;
    }
    @GetMapping("/")
    public String homepage(){
        return "home";
    }
    @GetMapping("/form")
    public String loginpage(Model model){
        model.addAttribute("userInfo", new UserInfo());
        return "form";
    }
    @GetMapping("/help")
    public  String help(){
        return "check";
    }
    @PostMapping("/save")
    public String registerUser(@ModelAttribute UserInfo userInfo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "form";
        }
        try{
            userService.createUser(userInfo);
            model.addAttribute("success","Successfully Registered");
        }catch (UserAlreadyExistsException e){
            model.addAttribute("userpresent",e.getMessage());
            return "form";
        }
        return "home";

    }
    @PostMapping("/login")
    public String loginUSer(@ModelAttribute UserInfo userInfo , Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }
        boolean isAuthenticate = customUserService.loginUser(userInfo.getEmail(), userInfo.getPassword());
        if (isAuthenticate) {
            return "home";
        } else {
            model.addAttribute("loginError", "Email or password is incorrect");
            return "form";
        }
    }
    }


