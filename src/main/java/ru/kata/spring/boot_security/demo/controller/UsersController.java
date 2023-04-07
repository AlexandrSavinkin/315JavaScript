package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
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
//        return "/reg";
//    }
    @GetMapping("/")
    public String startPage() {
        return "redirect:/login";
    }


    @GetMapping("/user")
    public String pageForAuthenticatedUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute(user);
        if (user.getRoles().contains(roleRepository.getById(2))) {
            return "show";
        } else
            return "user";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model, Principal principal, @ModelAttribute("updatedUser") User updatedUser) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("principalUser", user);
        model.addAttribute("users", userService.getAllUsers());
        Collection<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "all_users";
    }

//    @GetMapping("/admin/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.show(id));
//        return "show";
//    }
//
//    @GetMapping("/user/{id}")
//    public String showForUser(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.show(id));
//        return "user";
//    }

    @GetMapping("/admin/new")
    public String newPerson(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("principalUser", user);
        model.addAttribute("user", new User());
        Collection<Role> roles = roleRepository.findAll();
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
        Collection<Role> roles = roleRepository.findAll();
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

    @GetMapping("/reg")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "/reg";
        return "redirect:/start_page";
    }


}