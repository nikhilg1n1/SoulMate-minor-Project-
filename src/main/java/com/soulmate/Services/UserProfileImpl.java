package com.soulmate.Services;

import com.soulmate.Entites.UserProfile;
import com.soulmate.Repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileImpl {
    private final UserProfileRepository userProfileRepository;

    public UserProfileImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void saveProfile(UserProfile userProfile){
         userProfileRepository.save(userProfile);
    }
}
