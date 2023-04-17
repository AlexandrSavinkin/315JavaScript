package ru.kata.spring.boot_security.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import java.util.Set;

public class UserDTO {
    @NotEmpty(message = "Here should be the name!")
    @Size(min = 2, max = 30, message = "Number of characters from 2 to 30")
    private String name;

    @NotEmpty(message = "Here should be the lastname!")
    @Size(min = 2, max = 30, message = "Number of characters from 2 to 30")
    private String lastname;

    @Min(value = 0, message = "Age must be greater than 0")
    private int age;

    @Email
    @NotEmpty(message = "Here should be the email!")
    @Size(min = 2, max = 30, message = "Number of characters from 2 to 30")
    private String username;

    @NotEmpty(message = "Here should be the password!")
    private String password;

//    @JsonProperty
    private Set<Role> roles;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> role) {
        this.roles = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}


