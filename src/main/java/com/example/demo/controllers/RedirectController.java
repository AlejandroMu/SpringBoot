package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.security.JwtService;


@Controller
@RequestMapping("/")
public class RedirectController {

    @Autowired
    JwtService jwtTokenProvider;
    
    @GetMapping("/redirect/{path}")
    public String redirect(Authentication auth, @PathVariable String path) {
        String token = jwtTokenProvider.generateToken(auth);
        String reacthost = "http://localhost:3000/";
        return "redirect:"+reacthost+path+"?token="+token;
    }
    
}
