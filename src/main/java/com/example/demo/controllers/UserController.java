package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.services.interfaces.UserService;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;  
    

    @GetMapping("/create-user")
    public String createTemplete(User user, Model model) {
        return "create-user";
    }

    @PostMapping("/create")
    public String createUser(User user, Model model) {
        userService.saveUser(user);
        return "redirect:/";
    }
    

    @GetMapping
    public String home(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "list-users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable Integer id) {

        User userEdit = userService.getUserById(id);
        model.addAttribute("user", userEdit);
        return "edit-user";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user, Model model) {
        userService.updateUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String getMethodName(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
    
    


}

