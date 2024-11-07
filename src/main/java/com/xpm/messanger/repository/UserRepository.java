package com.xpm.messanger.repository;

import com.xpm.messanger.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
