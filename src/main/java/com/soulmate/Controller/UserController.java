package com.soulmate.Controller;

import com.soulmate.Entites.UserInfo;
import com.soulmate.Exceptions.UserAlreadyExistsException;
import com.soulmate.Services.CustomUserService;
import com.soulmate.Services.EmailService;
import com.soulmate.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UserController {

   private  final UserService userService;
   private final CustomUserService customUserService;
   private  String generatedOtp;

   private final EmailService emailService;

   private final HttpSession session;
    public UserController(UserService userService, CustomUserService customUserService, EmailService emailService, HttpSession session) {
        this.userService = userService;
        this.customUserService = customUserService;
        this.emailService = emailService;
        this.session = session;
    }
    @GetMapping("/")
    public String homepage(Model model,Authentication authentication,UserInfo userInfo){
        if(authentication!=null &&  authentication.isAuthenticated()){
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            String username=userDetails.getUsername();
            String firstname=userInfo.getUserfirstname();
            model.addAttribute("firstname",firstname);
            return "home";
        }
        return "home";
    }

    @GetMapping("/profile")
    public String profilePage(Model model,Authentication authentication){
        if(authentication!=null &&  authentication.isAuthenticated()){
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            String username =userDetails.getUsername();
            model.addAttribute("username",username);
        }
        return "profile";
    }
    @GetMapping("/form")
    public String loginpage(Model model,RedirectAttributes redirectAttributes){
        model.addAttribute("userInfo", new UserInfo());
//
//        return "form";
        model.addAttribute("verified", model.asMap().get("verified"));

        model.addAttribute("error", model.asMap().get("error"));
        model.addAttribute("success", model.asMap().get("success"));
        return "form";
    }

    @PostMapping("/logout")
    public String logoutUser(){
        return "home";
    }

    @GetMapping("/help")
    public  String help(){
        return "check";
    }

    @GetMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("userInfo", new UserInfo());
        return "register";
    }

    @GetMapping("/otppage")
    public String verifyotp(Model model){
        UserInfo userInfo =(UserInfo) session.getAttribute("userInfo");
        if(userInfo==null){
            return "redirect:/register";
        }
        model.addAttribute("userInfo",userInfo);
        return "otpVerification";
    }
    @PostMapping("/save")
    public String registerUser(@Valid @ModelAttribute("userInfo") UserInfo userInfo, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }
        try{
            String otp=emailService.generateOtp();
            System.out.println(STR."generated otp" +otp);
            System.out.println(generatedOtp);
            session.setAttribute("otp",otp);
            session.setAttribute("userInfo",userInfo);
            emailService.sendOtp(userInfo.getEmail(),otp);
            return "redirect:/otppage";
        }catch (UserAlreadyExistsException e){
            redirectAttributes.addFlashAttribute("userpresent","User already exists");
            return "redirect:/register";
        }
    }
    @PostMapping("/login")
    public String loginUSer(@ModelAttribute UserInfo userInfo , Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "form";
        }
        boolean isAuthenticate = customUserService.loginUser(userInfo.getEmail(), userInfo.getPassword());
        if (isAuthenticate) {
//            Authentication authentication = SecurityContextHolder .getContext().getAuthentication();
//            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
//            String username= userDetails.getUsername();

//            model.addAttribute("username",username);
            return "home";
        }
        else {
            model.addAttribute("loginError", "Email or password is incorrect");
            return "form";
        }

    }
//      @PostMapping("/register")
//        public  String registerUser(@RequestParam("email"),String email,Model model){
//            generatedOtp= emailService.sendOtp(email);
//           model.addAttribute("email",email);
//
//           return "otpVerfication";
//        }
        @PostMapping("/verifyotp")
        public String verifyOtp(@RequestParam("email") String email,@RequestParam("otp") String otp,Model model, RedirectAttributes redirectAttributes ){
        String sessionOtp=(String) session.getAttribute("otp");
        UserInfo userInfo=(UserInfo) session.getAttribute("userInfo");

            System.out.println(STR."Email\{email}");
            System.out.println(STR."Enterd otp\{otp}");
            System.out.println(STR."Session otp\{sessionOtp}");
            System.out.println(STR."UserInfo from Session\{userInfo}");


            if(userInfo==null){
                redirectAttributes.addFlashAttribute("sessionerror","The Session has been expired please try again letter");
                System.out.println("Session Expired Redirecting to register page");
                return "redirect:/register";
            }
            if(otp.equals(sessionOtp)){
                System.out.println("Otp Matches redirecting form page");
                session.removeAttribute("otp");
                session.removeAttribute("userInfo");
                userService.createUser(userInfo);

                redirectAttributes.addFlashAttribute("verified","Email Verified Successfully");
                redirectAttributes.addFlashAttribute("userInfo",new UserInfo());
                return "redirect:/form";
            }
            else if(!otp.equals((sessionOtp))){
                redirectAttributes.addFlashAttribute("error","Invalid otp, please try again");
                System.out.println("Invalid otp Redirecting to Otp Verification page ");
                return "redirect:/otppage";
            }

            if (!otp.equals(sessionOtp)) {
                redirectAttributes.addFlashAttribute("success", "Registration complete");
                return "redirect:/form";
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid otp");
                return "redirect:/otppage";

            }
        }
}


