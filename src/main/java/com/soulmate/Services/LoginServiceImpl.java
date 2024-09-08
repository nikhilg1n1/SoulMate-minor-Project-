package com.soulmate.Services;

import com.soulmate.Dto.RegistrationDto;
import com.soulmate.Entites.UserRegistrationInfo;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


public class LoginServiceImpl implements UserDetails {
    private final UserRegistrationInfo userRegistrationInfo;
//    private final Optional<UserRegistrationInfo> userRegistrationInfo;

    public LoginServiceImpl(UserRegistrationInfo userRegistrationInfo) {

        this.userRegistrationInfo = userRegistrationInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return userRegistrationInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userRegistrationInfo.getEmail();
    }
}
