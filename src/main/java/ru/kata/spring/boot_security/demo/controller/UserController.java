package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userDao;

    @Autowired
    public UserController(UserService userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/user")
    public String getUser(Principal principal, ModelMap model) {
        model.addAttribute("user", userDao.getUserByName(principal.getName()));
        return "usr";
    }
}