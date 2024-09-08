package com.soulmate.Services;

//import com.soulmate.Entites.UserInfo;
//import com.soulmate.Entites.UserInfo;
import com.soulmate.Entites.UserRegistrationInfo;
import com.soulmate.Repository.UserRegistrationRepo;
//import com.soulmate.Repository.UserRepository;
//import com.soulmate.Repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserService implements UserService {

    private final UserRegistrationRepo userRegistrationRepo;

//    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public CustomUserService(UserRegistrationRepo userRegistrationRepo ,PasswordEncoder passwordEncoder) {
        this.userRegistrationRepo = userRegistrationRepo;

//        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Optional<UserRegistrationInfo> FindByUsername(String firstname) {
        return userRegistrationRepo.findByFirstname(firstname);
    }

    @Override
    public Optional<UserRegistrationInfo> FindByEmail(String email) {
        return userRegistrationRepo.findByEmail(email);
    }

    @Override
    public boolean checkEmail(String email) {
        return userRegistrationRepo.existsByEmail(email);
    }
    public boolean loginUser(String email, String password){
        Optional<UserRegistrationInfo> UserRegistration = userRegistrationRepo.findByEmail(email);
        if (UserRegistration.isPresent()) {
            System.out.println("User found: " + UserRegistration.get().getEmail());
            System.out.println("Entered password: " + password);
            System.out.println("Stored password: " + UserRegistration.get().getPassword());
            return  passwordEncoder.matches(password, UserRegistration.get().getPassword());
        }
        return false;
    }

}
