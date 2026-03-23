package com.soulmate.Controller;

import com.soulmate.Entites.UserProfile;
import com.soulmate.Repository.UserProfileRepository;
import com.soulmate.Services.UserProfileImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;

@Controller
public class UserProfileController {

    private final UserProfileImpl userProfileImpl;

    private final UserProfileRepository userProfileRepository;

    public UserProfileController(UserProfileImpl userProfileImpl, UserProfileRepository userProfileRepository) {
        this.userProfileImpl = userProfileImpl;
        this.userProfileRepository = userProfileRepository;
    }


    @PostMapping("/save-profile")
    public String saveProfile(@ModelAttribute("UserProfile") UserProfile userProfile, Model model, RedirectAttributes redirectAttributes) {
        // Save the profile including the uploaded picture
        userProfileRepository.save(userProfile);

        // Convert the profile picture byte array to Base64 string
        if (userProfile.getProfilePicture() != null) {
            String base64ProfilePic = Base64.getEncoder().encodeToString(userProfile.getProfilePicture());
            model.addAttribute("profilePic", base64ProfilePic);
        } else {
            model.addAttribute("profilePic", null); // Add null if there's no profile picture
        }

        // Return to the page where the profile should be shown, e.g., a profile page
        return "redirect:/home";
    }

}
