package com.example.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @GetMapping("/login")
    public String loginUSer(User user, Model model) {
        return "login";
    }

    @PostMapping("/success")
    public String afterLogin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println(principal);
        System.out.println("POST METHOD CALLED");
        return "redirect:/users";
    }
}
