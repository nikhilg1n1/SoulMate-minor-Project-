package com.soulmate.Entites;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Username is required")
    private String username;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Minimum 6 digit password is required")
    @Size(min = 6)
    private String password;


}
