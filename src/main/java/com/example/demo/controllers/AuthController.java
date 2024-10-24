package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.security.JwtService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class AuthController {
    
    @Autowired
    JwtService jwtTokenProvider;

    @GetMapping("/auth/login")
    public String loginUSer(User user, Model model) {
        return "login";
    }

    @PostMapping("/auth/success")
    public String afterLogin(Authentication auth,Model model, HttpServletResponse response ) {
        if(auth.isAuthenticated()) {
            String token = jwtTokenProvider.generateToken(auth);
            response.addHeader("token", token);
            return "redirect:http://localhost:8080/home?token="+token;
        }
        return "home";
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
