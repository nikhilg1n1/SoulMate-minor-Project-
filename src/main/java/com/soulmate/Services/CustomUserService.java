package com.soulmate.Services;

import com.soulmate.Entites.UserRegistrationInfo;
import com.soulmate.Repository.UserRegistrationRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class CustomUserService implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRegistrationRepo userRegistrationRepo;
    private final UserRegistrationInfo userRegistrationInfo;

    @Lazy
    public CustomUserService(PasswordEncoder passwordEncoder, UserRegistrationRepo userRegistrationRepo, UserRegistrationInfo userRegistrationInfo) {
        this.passwordEncoder = passwordEncoder;
        this.userRegistrationRepo = userRegistrationRepo;
        this.userRegistrationInfo = userRegistrationInfo;
    }

    // In your UserRegistrationService or Controller where user data is saved
    public void createUser(UserRegistrationInfo userRegistrationInfo) {
        if (userRegistrationRepo.findByEmail(userRegistrationInfo.getEmail().toLowerCase()).isPresent()) {
            throw new UsernameNotFoundException("User already exists");
        }
        // Convert email to lowercase before saving
        userRegistrationInfo.setEmail(userRegistrationInfo.getEmail().toLowerCase());
        userRegistrationInfo.setPassword(passwordEncoder.encode(userRegistrationInfo.getPassword()));
        userRegistrationRepo.save(userRegistrationInfo);
    }


    @Override
    public boolean checkEmail(String email) {
        return userRegistrationRepo.existsByEmail(email);
    }
    public boolean loginUser(String email, String password){
        Optional<UserRegistrationInfo> UserRegistration = userRegistrationRepo.findByEmail(email);
        if (UserRegistration.isPresent()) {
            return  passwordEncoder.matches(password, UserRegistration.get().getPassword());
        }
        return false;
    }
}


