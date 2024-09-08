package com.soulmate.Controller;

import com.soulmate.Dto.RegistrationDto;
//import com.soulmate.Entites.UserInfo;
import com.soulmate.Entites.UserRegistrationInfo;
import com.soulmate.Exceptions.UserAlreadyExistsException;
//import com.soulmate.Repository.UserRepository;
import com.soulmate.Repository.UserRegistrationRepo;
import com.soulmate.Services.CustomUserService;
import com.soulmate.Services.EmailService;
import com.soulmate.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserRegistrationController {

   private  final UserService userService;

   private final CustomUserService customUserService;
   private final UserRegistrationRepo userRegistrationRepo;


   private  String generatedOtp;

   private final EmailService emailService;

   private final HttpSession session;
//   private final UserRepository userRepository;
    public UserRegistrationController(UserService userService, CustomUserService customUserService, UserRegistrationRepo userRegistrationRepo, EmailService emailService, HttpSession session) {
        this.userService = userService;
        this.customUserService = customUserService;
        this.userRegistrationRepo = userRegistrationRepo;
        this.emailService = emailService;
        this.session = session;
//        this.userRepository = userRepository;
    }
//    @GetMapping("/")
//    public String homepage(Model model,Authentication authentication){
//        if(authentication!=null &&  authentication.isAuthenticated()){
//            System.out.println(authentication.getName());
//            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
//            String email =userDetails.getUsername();
//            System.out.println(STR."username is\{email}");
//            Optional<UserInfo> user= userRepository.findByEmail(email);
//            if(user.isPresent()){
//                UserInfo userInfo=user.get();
//                model.addAttribute("firstname",userInfo.getFirstname());
//                model.addAttribute("userInfo",userInfo);
//            }
//        }
//        return "home";
//    }
//@GetMapping("/home")
//public String homepage(Model model) {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String firstname;
//
//    System.out.println("Authentication" +authentication);
//    if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof UserDetails)) {
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//         firstname = userDetails.getUsername();
//        Optional<UserInfo> user = userService.FindByUsername(firstname);
//        if (user.isPresent()) {
//            UserInfo userInfo = (UserInfo) authentication.getPrincipal();
//            model.addAttribute("userInfo", userInfo);
//            System.out.println("user info is" + userInfo);
//        }
//    }
//    System.out.println("end");
//    return "home";
//}

    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication ,UserRegistrationInfo userRegistrationInfo) {
        System.out.println("User email is "+userRegistrationRepo.findByEmail(userRegistrationInfo.getEmail()));
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Authenticated user: " + authentication.getName());
            UserDetails userDetails= (UserDetails) authentication.getPrincipal();
            String email= userDetails.getUsername();
            // Add user-specific data to the model
            Optional<UserRegistrationInfo>userRegistrationInfo1=userRegistrationRepo.findByEmail(email);
            if (userRegistrationInfo1.isPresent()){
                UserRegistrationInfo userRegistrationInfo2=userRegistrationInfo1.get();
                model.addAttribute("userInfo",userRegistrationInfo2);
            }



        } else {
            System.out.println("No authenticated user found.");
        }
        return "home";
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
        model.addAttribute("registrationDto", new UserRegistrationInfo());
        return "register";
    }

    @GetMapping("/otppage")
    public String verifyotp(Model model){
//        UserInfo userInfo =(UserInfo) session.getAttribute("userInfo");
        UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) session.getAttribute("registrationDto");
        if(userRegistrationInfo==null){
            return "redirect:/register";
        }
        model.addAttribute("registrationDto",userRegistrationInfo);
        return "otpVerification";
    }

    @PostMapping("/save")
    public String registerUser(@Valid @ModelAttribute("registrationDto")UserRegistrationInfo userRegistrationInfo, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("check","just checking");
            return "register";
        }
            boolean emailChecked= customUserService.checkEmail(userRegistrationInfo.getEmail());
        if(emailChecked){
            model.addAttribute("emailExists","User already Exists");
        }
        else {

            try {
                String otp = emailService.generateOtp();
                System.out.println(STR."generated otp" + otp);
                System.out.println(generatedOtp);
                session.setAttribute("otp", otp);
                session.setAttribute("registrationDto", userRegistrationInfo);
                emailService.sendOtp(userRegistrationInfo.getEmail(), otp);
                return "redirect:/otppage";
            } catch (UserAlreadyExistsException e) {
                redirectAttributes.addFlashAttribute("userpresent", "User already exists");
                return "redirect:/register";
            }

        }
        return "redirect:register";
    }
    @PostMapping("/verifyotp")
        public String verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model, RedirectAttributes redirectAttributes){
        String sessionOtp=(String) session.getAttribute("otp");
        UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) session.getAttribute("registrationDto");

            System.out.println(STR."Email\{email}");
            System.out.println(STR."Enterd otp\{otp}");
            System.out.println(STR."Session otp\{sessionOtp}");
            System.out.println(STR."UserInfo from Session\{userRegistrationInfo}");

            if(userRegistrationInfo==null && sessionOtp==null){
                redirectAttributes.addFlashAttribute("sessionerror","The Session has been expired please try again letter");
                System.out.println("Session Expired Redirecting to register page");
                return "redirect:/register";
            }
            if(otp.equals(sessionOtp)){
                System.out.println("Otp Matches redirecting form page");
                System.out.println(userRegistrationInfo);

                session.removeAttribute("otp");
                session.removeAttribute("registrationDto");
                userService.createUser(userRegistrationInfo);
                System.out.println(userRegistrationInfo);

                redirectAttributes.addFlashAttribute("verified","Email Verified Successfully");
                System.out.println(userRegistrationInfo);
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


