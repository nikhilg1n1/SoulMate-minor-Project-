package com.soulmate.Repository;

import com.soulmate.Entites.UserRegistrationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegistrationRepo extends JpaRepository<UserRegistrationInfo,Long> {

   Optional<UserRegistrationInfo> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<UserRegistrationInfo> findByFirstname(String firstname);

}
