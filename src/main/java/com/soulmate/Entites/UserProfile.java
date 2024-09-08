package com.soulmate.Entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_profiles")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Gender cannot be null")
    @Size(min = 4, max = 10, message = "Gender must be between 4 and 10 characters")
    private String gender;

    @NotNull(message = "Date of Birth cannot be null")
    private LocalDate dob;

    @Size(max = 255, message = "Profile Picture path must be less than 255 characters")
    private String profilePicture;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
    private String phone;

    @Size(max = 10, message = "Height must be less than 10 characters")
    private String height;

    @Size(max = 10, message = "Weight must be less than 10 characters")
    private String weight;

    @Size(max = 20, message = "Marital Status must be less than 20 characters")
    private String maritalStatus;

    @Size(max = 20, message = "Children field must be less than 20 characters")
    private String children;

    @Size(max = 50, message = "Religion must be less than 50 characters")
    private String religion;

    @Size(max = 50, message = "Caste/Community must be less than 50 characters")
    private String caste;

    @Size(max = 50, message = "Mother Tongue must be less than 50 characters")
    private String motherTongue;

    @Size(max = 50, message = "Nationality must be less than 50 characters")
    private String nationality;

    @Size(max = 100, message = "Education must be less than 100 characters")
    private String education;

    @Size(max = 100, message = "Occupation must be less than 100 characters")
    private String occupation;

    @Size(max = 50, message = "Income must be less than 50 characters")
    private String income;

    @Size(max = 50, message = "Diet must be less than 50 characters")
    private String diet;

    @Size(max = 50, message = "Smoking must be less than 50 characters")
    private String smoking;

    @Size(max = 50, message = "Drinking must be less than 50 characters")
    private String drinking;

    @Size(max = 500, message = "Hobbies and Interests must be less than 500 characters")
    private String hobbies;

    @Size(max = 50, message = "Family Type must be less than 50 characters")
    private String familyType;

    @Size(max = 100, message = "Parents' Occupation must be less than 100 characters")
    private String parentsOccupation;

    @Size(max = 50, message = "Siblings field must be less than 50 characters")
    private String siblings;

    @Size(max = 1000, message = "About Me must be less than 1000 characters")
    private String aboutMe;

    @Size(max = 1000, message = "Expectations from Partner must be less than 1000 characters")
    private String partnerExpectations;

    // Getters and Setters

    // Constructor, if needed

}
