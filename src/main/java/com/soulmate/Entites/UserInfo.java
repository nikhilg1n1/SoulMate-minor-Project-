package com.soulmate.Entites;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User")
@Builder
public class UserInfo   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Username is required")
    private String userfirstname;

    private String userlastname;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "6 digit password is required")
    @Size(min = 6 ,message = "6 Letters Password is Required")
    private String password;

    private Role role;


}
