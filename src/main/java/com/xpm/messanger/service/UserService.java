package com.xpm.messanger.service;


import com.xpm.messanger.entity.User;
import com.xpm.messanger.exceptions.ServiceException;
import com.xpm.messanger.repository.UserRepository;
import com.xpm.messanger.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.control.MappingControl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserLogin = authentication.getName();
            return this.findUserBy(currentUserLogin);
        } else {
            throw new ServiceException("Could not determine the current user!");
        }
    }

    public void saveUser(User userForSave) throws ServiceException {
        try {
            this.userRepository.findAll().forEach(user -> {
                if (userForSave.getLogin().equals(user.getLogin())) {
                    throw new ServiceException(String.format("User with login - %s already exists!", user.getLogin()));
                }
                if (userForSave.getEmail().equals(user.getEmail())) {
                    throw new ServiceException(String.format("User with email - %s already exists!", user.getLogin()));
                }
            });
            this.userRepository.save(userForSave);
        } catch (TransactionSystemException exception) {
            throw new ServiceException("Failed to create user!");
        }
    }

    public User findUserBy(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElse(null);
    }

    public User findUserBy(String login) {
        Optional<User> user = this.userRepository.findUserByLogin(login);
        return user.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }


}
