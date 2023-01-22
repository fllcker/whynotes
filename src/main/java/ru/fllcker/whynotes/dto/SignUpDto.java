package ru.fllcker.whynotes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpDto {
    @Email(message = "Email is not valid!")
    private String email;
    @Size(min = 6, message = "Password should be more than 6 letters!")
    private String password;
    @Size(min = 1, max = 64, message = "Name should be more than 1 letter and less than 64 letters!")
    private String name;
}
