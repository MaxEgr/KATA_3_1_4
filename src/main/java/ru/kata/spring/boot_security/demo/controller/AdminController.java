package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }

    @GetMapping("/admin")
    public String getUsers(Principal principal, Model model) {
        model.addAttribute(this.userService.getUserByName(principal.getName()));
        model.addAttribute("usersList", this.userService.getAllUsers());
        model.addAttribute("roleList", this.roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/admin/new")
    public String createUser(@ModelAttribute("user") User user) {
        this.userService.addUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/admin/update")
    public String updateUser(@ModelAttribute("user") User user) {
        this.userService.editUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        this.userService.deleteUser(id);
        return "redirect:/admin";
    }
}
