package com.example.demo.controllers.restControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.security.JwtService;




/**
 * StudentController
 */
@RestController
@RequestMapping("/api")
public class StudentController {

    private static List<Student> students = new ArrayList<>();

    @Autowired
    private JwtService jwtTokenProvider;

    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/students")
    public List<Student> getMethodName() {
        return students;
    }

    @PostMapping("/students")
    public String postMethodName(@RequestBody Student student) {
        students.add(student);
        simpleMessagingTemplate.convertAndSend("/topic/students", "new student");
        return "Estudiante agregado";
    }

    @PostMapping("/login")
    public ResponseEntity<?> postMethodName(@RequestBody AuthDto entity) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword()));
        if(authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(jwtTokenProvider.generateToken(authentication));
        }
        return ResponseEntity.badRequest().body("Usuario o contraseña incorrectos");
    }
    
    
    
}