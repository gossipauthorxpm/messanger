package com.xpm.messanger.controller;

import com.xpm.messanger.dto.user.AuthUserDto;
import com.xpm.messanger.dto.user.RegisterUserDto;
import com.xpm.messanger.exceptions.ServiceException;
import com.xpm.messanger.http.HttpResponse;
import com.xpm.messanger.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth/")
@Tag(name = "Авторизация", description = "Авторизация и регистрация пользователей в систему")
public class AuthController {

    private AuthService authService;

    @Operation(summary = "Регистрация")
    @PostMapping("register")
    public HttpResponse register(@RequestBody @Valid RegisterUserDto userDto) {
        try {
            this.authService.registerUser(userDto);
            return new HttpResponse(HttpStatus.OK, "User is registered successfully!");
        } catch (ServiceException exception) {
            return new HttpResponse(HttpStatus.CONFLICT, exception.getMessage());
        }
    }

    @Operation(summary = "Авторизация")
    @PostMapping("login")
    public HttpResponse auth(@RequestBody @Valid AuthUserDto userDto) {
        try {
            return new HttpResponse(HttpStatus.OK, "User auth successfully!", this.authService.authUser(userDto));
        }catch (ServiceException exception) {
            return new HttpResponse(HttpStatus.FORBIDDEN, exception.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Обновление токена")
    @PutMapping("login")
    public void restore() {
        this.authService.restoreUserToken();
    }
}
