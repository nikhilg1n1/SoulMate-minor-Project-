package com.soulmate.Controller;

//import com.soulmate.Entites.UserInfo;
import com.soulmate.Entites.UserRegistrationInfo;
import com.soulmate.Repository.UserRegistrationRepo;
//import com.soulmate.Repository.UserRepository;
import com.soulmate.Services.CustomUserService;
import com.soulmate.Services.LoginUserService;
import jakarta.persistence.GeneratedValue;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.classfile.Opcode;
import java.util.Optional;

@Controller
public class UserLoginController {

//    private final UserRepository userRepository;
    private final LoginUserService loginUserService;

    private  final CustomUserService customUserService;
    private UserRegistrationInfo userRegistrationInfo;
    private final UserRegistrationRepo userRegistrationRepo;
    private final PasswordEncoder passwordEncoder;

    public UserLoginController(LoginUserService loginUserService, CustomUserService customUserService, UserRegistrationRepo userRegistrationRepo, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
        this.loginUserService = loginUserService;
        this.customUserService = customUserService;
        this.userRegistrationRepo = userRegistrationRepo;
        this.passwordEncoder = passwordEncoder;

    }
    @PostMapping("/login")
    public String userLogin(@ModelAttribute("userInfo") UserRegistrationInfo userRegistrationInfo, Model model, RedirectAttributes redirectAttributes, BindingResult result) {

        System.out.println(userRegistrationInfo);
        if (result.hasErrors()) {
            model.addAttribute("formError","Something went wrong");
            System.out.println(result.hasErrors());
            return "form";
        }

        System.out.println("Submitted email: " + userRegistrationInfo.getEmail());
        System.out.println("Submitted password: " + userRegistrationInfo.getPassword());
        System.out.println("nikhil");
        boolean isAuthenticate= customUserService.loginUser(userRegistrationInfo.getEmail(),userRegistrationInfo.getPassword());
        System.out.println(isAuthenticate);
//        Optional<UserRegistrationInfo> user = userRegistrationRepo.findByEmail(userRegistrationInfo.getEmail());
//        System.out.println("look here" + user);
        if (isAuthenticate) {
                model.addAttribute("Success","login done Successfully");
                return "redirect:/home";
        }
        else {
                System.out.println("password is incorrect");
                model.addAttribute("Invalid", "Email or password is incorrect");
                return "form";
        }

    }
    @GetMapping("/form")
    public String loginPage(Model model){
        model.addAttribute("userInfo",new UserRegistrationInfo());
        return "form";
    }

      @GetMapping("/profile")
        public String profilePage(Model model, Authentication authentication) {
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName(); // Get the username (email) from Authentication
                Optional<UserRegistrationInfo> user = userRegistrationRepo.findByEmail(email);
                if (user.isPresent()) {
                    UserRegistrationInfo userRegistrationInfo1= user.get();
                    model.addAttribute("firstname", userRegistrationInfo1);
                    return "profile";
                } else {
                    model.addAttribute("error", "User not found.");
                    return "form";
                }
            } else {
                return "redirect:form";
            }
        }
}