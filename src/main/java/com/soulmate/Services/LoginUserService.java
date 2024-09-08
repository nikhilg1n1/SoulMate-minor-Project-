package com.soulmate.Services;

import com.soulmate.Entites.UserRegistrationInfo;
import com.soulmate.Repository.UserRegistrationRepo;
//import com.soulmate.Repository.UserRepository;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LoginUserService implements UserDetailsService {

    private final CustomUserService customUserService;

//    private final UserRepository userRepository;
    private UserRegistrationRepo userRegistrationRepo;
    private final PasswordEncoder passwordEncoder;

    public LoginUserService(CustomUserService customUserService, UserRegistrationRepo userRegistrationRepo, PasswordEncoder passwordEncoder) {
        this.customUserService = customUserService;
//        this.userRepository = userRepository;
        this.userRegistrationRepo = userRegistrationRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Convert email to lowercase before searching in the database
        System.out.println("Attempting to load user by email: " + email.toLowerCase());
        Optional<UserRegistrationInfo> userRegistrationInfo = userRegistrationRepo.findByEmail(email.toLowerCase());

        if (userRegistrationInfo.isPresent()) {
            System.out.println("User found: " + userRegistrationInfo.get().getEmail());
            return new LoginServiceImpl(userRegistrationInfo.get());
        } else {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
    }



}
