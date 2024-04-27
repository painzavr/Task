package com.example.demo.user;

import com.example.demo.annotation.ageValidator.ValidAge;
import com.example.demo.annotation.birthdateValidator.ValidBirthdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Setter
@Getter
public class UserDTO {
    @NotNull
    @Email(message = "Must be in email address format")
    private String email;

    @NotEmpty(message = "Must not be empty")
    private String firstName;

    @NotEmpty(message = "Must not be empty")
    private String lastName;

    @NotNull
    @ValidBirthdate
    @ValidAge
    private LocalDate birthDate;

    private String address;
    private String phoneNumber;
}
