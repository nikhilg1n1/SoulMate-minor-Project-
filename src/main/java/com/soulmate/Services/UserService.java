package com.soulmate.Services;

import com.soulmate.Dto.RegistrationDto;
//import com.soulmate.Entites.UserInfo;
import com.soulmate.Entites.UserRegistrationInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;

import java.util.Optional;

public interface UserService {

    void createUser(UserRegistrationInfo userRegistrationInfo);

    Optional<UserRegistrationInfo>FindByUsername(String firstname);

Optional<UserRegistrationInfo>FindByEmail(String email);

boolean checkEmail(String email);

}
