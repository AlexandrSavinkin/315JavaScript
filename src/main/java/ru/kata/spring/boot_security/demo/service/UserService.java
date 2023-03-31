package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User show(int id);
    void save(User user);
    void update(int id, User updatedUser);
    void delete(int id);
    User findByUsername(String username);


}