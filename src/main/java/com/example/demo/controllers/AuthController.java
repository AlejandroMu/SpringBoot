package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.security.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class AuthController {
    
    @Autowired
    JwtService jwtTokenProvider;

    @GetMapping("/auth/login")
    public String loginUSer(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        String refererUrl = request.getHeader("Referer");
        Cookie cookie = new Cookie("refererUrl", refererUrl);
        cookie.setPath("/auth");
        response.addCookie(cookie);
        return "login";
    }

    @PostMapping("/auth/success")
    public String afterLogin(Authentication auth,Model model, HttpServletResponse response, @CookieValue(value = "refererUrl", defaultValue = "/home") String refererUrl) {
        String token = jwtTokenProvider.generateToken(auth);
        response.addHeader("token", token);
        return "redirect:"+refererUrl+"?token="+token;
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

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/auth/login";
    }
}
