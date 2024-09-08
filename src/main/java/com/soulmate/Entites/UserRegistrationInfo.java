package com.soulmate.Entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Optional;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Userdetails")
@Builder
public class UserRegistrationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Username is required")
    private String firstname;

    private String  lastname;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "6 digit password is required")
    @Size(min = 6 ,message = "6 Letters Password is Required")
    private String password;


}
