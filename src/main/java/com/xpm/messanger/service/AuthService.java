package com.xpm.messanger.service;

import com.xpm.messanger.dto.user.AuthUserDto;
import com.xpm.messanger.dto.user.RegisterUserDto;
import com.xpm.messanger.entity.User;
import com.xpm.messanger.exceptions.ServiceException;
import com.xpm.messanger.mapper.UserMapper;
import com.xpm.messanger.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private UserMapper userMapper;
    private UserService userService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;


    public void registerUser(RegisterUserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = this.userMapper.registerToUser(userDto);
        this.userService.saveUser(user);
    }

    public String authUser(AuthUserDto userDto) {
        User user = this.userService.findUserBy(userDto.getUsername());
        if (user == null) {
            throw new ServiceException(String.format("User with login - %s not found!", userDto.getUsername()));
        }
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new ServiceException("Wrong password!");
        }
        return this.createToken(userDto);
    }

    public void restoreUserToken() {
        User currentUser = this.userService.getCurrentUser();
    }

    private String createToken(AuthUserDto request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = this.userService.findUserBy(request.getUsername());
        return this.jwtService.generateToken(user);
    }


}
