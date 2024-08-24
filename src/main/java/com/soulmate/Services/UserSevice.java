package com.soulmate.Services;

import com.soulmate.Entites.UserInfo;
import org.springframework.stereotype.Component;

@Component
public interface UserSevice {
public void createUser(UserInfo userInfo);
}
