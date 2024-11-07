package com.xpm.messanger.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @Email(message = "Enter valid Email")
    private String email;
}
