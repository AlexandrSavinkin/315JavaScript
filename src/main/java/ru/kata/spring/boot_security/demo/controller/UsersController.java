package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.reposotiries.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collection;



@Controller
@RequestMapping("/")
public class UsersController {
    private final UserService userService;
    @Autowired
    public RoleRepository roleRepository;


    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
//    @GetMapping("/")
//    public String startPage() {
//        return "/registration";
//    }
    @GetMapping("/")
    public String startPage() {
        return "/start_page";
    }


    @GetMapping("/user")
    public String pageForAuthenticatedUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute(user);
        return "user";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all_users";
    }

    @GetMapping("/admin/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "show";
    }

    @GetMapping("/user/{id}")
    public String showForUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "user";
    }

    @GetMapping("/admin/new")
    public String newPerson(Model model) {
        model.addAttribute("user", new User());
        Collection<Role> roles = (Collection<Role>) roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.show(id));
        Collection<Role> roles = (Collection<Role>)roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "edit";
    }

    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}