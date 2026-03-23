//package com.soulmate.Controller;
//
//import com.soulmate.Entites.UserProfile;
//import com.soulmate.Repository.UserProfileRepository;
//import com.soulmate.Services.UserProfileImpl;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.Base64;
//import java.util.Optional;
//
//@Controller
//public class UserProfileController1 {
//
//    private final UserProfileImpl userProfileImpl;
//    private final UserProfileRepository userProfileRepository;
//
//    public UserProfileController1(UserProfileImpl userProfileImpl, UserProfileRepository userProfileRepository) {
//        this.userProfileImpl = userProfileImpl;
//        this.userProfileRepository = userProfileRepository;
//    }
//
//    // Method to load profile page with user data if present
//    @GetMapping("/profile")
//    public String viewProfile(Authentication authentication, Model model) {
//        // Retrieve the logged-in user's email
//        String email = authentication.getName();
//
//        // Fetch the user profile data from the repository
//        Optional<UserProfile> userProfileOpt = userProfileRepository.findByEmail(email);
//
//        if (userProfileOpt.isPresent()) {
//            UserProfile userProfile = userProfileOpt.get();
//
//            // Convert profile picture to Base64 if present
//            if (userProfile.getProfilePicture() != null) {
//                String base64ProfilePic = Base64.getEncoder().encodeToString(userProfile.getProfilePicture());
//                model.addAttribute("profilePic", base64ProfilePic);
//            }
//
//            // Add user profile data to the model
//            model.addAttribute("userProfile", userProfile);
//        } else {
//            // No profile data found, prepare empty form
//            model.addAttribute("userProfile", new UserProfile());
//            model.addAttribute("profilePic", null); // no profile picture
//        }
//
//        // Return the profile view (replace 'profile' with your actual Thymeleaf template)
//        return "profile";
//    }
//}
