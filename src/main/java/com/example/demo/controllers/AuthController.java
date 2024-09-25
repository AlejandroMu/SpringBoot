package com.example.demo.controllers;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;

@Controller
@RequestMapping("/")
public class AuthController {
    
    @GetMapping("/auth/login")
    public String loginUSer(User user, Model model) {
        return "login";
    }

    @PostMapping("/auth/success")
    public String afterLogin(Authentication auth,Model model ) {
                
        return "redirect:/home";
    }

    @GetMapping("home")
    public String logoutUser(Model model) {
        return "home";
    }

    @GetMapping("/auth/error-403")
    public String error403(Model model) {
        model.addAttribute("errorMessage", "No tienes permisos para acceder a esta página.");
        return "error-403";
    }
}
