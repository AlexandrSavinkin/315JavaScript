package ru.kata.spring.boot_security.demo.controller;


import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/admins")
public class AdminRESTController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminRESTController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping
    public ResponseEntity<List<User>> showUsers() {
        List<User> userList = new ArrayList<>();

        for (User user : userService.getAllUsers()) {
            userList.add(user);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> showUser(@PathVariable("id") int id) {
        return new ResponseEntity<> (userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/userAuth")
    public ResponseEntity<User> showAuthUser() {
        return new ResponseEntity<> (userService.getCurrentUser(), HttpStatus.OK);
    }

    @PostMapping("/newAddUser")
    public ResponseEntity<HttpStatus> saveNewUser(@RequestBody User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return new ResponseEntity<> (HttpStatus.OK);
    }


    @PatchMapping("/users/{id}")
    public ResponseEntity<HttpStatus> userSaveEdit(@RequestBody @NotNull User user, @PathVariable Integer id) {
        user.setId(id);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user, id);

        return new ResponseEntity<> (HttpStatus.OK);
    }
}
