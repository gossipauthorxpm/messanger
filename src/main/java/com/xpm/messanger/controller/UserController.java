package com.xpm.messanger.controller;

import com.xpm.messanger.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "Authorization")
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "Пользователь", description = "Работа с учетными данными пользователя")
public class UserController {

    @Operation(summary = "Получить данные текущего пользователя")
    @GetMapping("")
    public HttpResponse getCurrentUser() {
        return new HttpResponse("Успешное получение данных");
    }

}
