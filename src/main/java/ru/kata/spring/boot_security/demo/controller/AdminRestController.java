package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.http.ResponseEntity;
import ru.kata.spring.boot_security.demo.reposotiries.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.PersonErrorResponse;
import ru.kata.spring.boot_security.demo.util.PersonNotCreatedException;
import ru.kata.spring.boot_security.demo.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class AdminRestController {
    @Autowired
    public RoleRepository roleRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminRestController(UserService userService
            , ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }



//    @GetMapping()
//    public List<UserDTO> getPeople() {
//        return userService.getAllUsers().stream().map(this::convertToUserDTO).collect(Collectors.toList());
//    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers().stream().map(this::convertToUserDTO).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("users", users);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public UserDTO show(@PathVariable("id") int id) {
        return convertToUserDTO(userService.show(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {

        System.out.println("----------------------------------"+userDTO.toString());

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(":");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        userService.save(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse errorResponse = new PersonErrorResponse("Person wih this id wasn't found!!!", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse errorResponse = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}