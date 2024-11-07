package com.xpm.messanger.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
