package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;




/**
 * StudentController
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    private static List<Student> students = new ArrayList<>();

    @GetMapping
    public List<Student> getMethodName() {
        return students;
    }

    @PostMapping
    public String postMethodName(@RequestBody Student student) {
        students.add(student);
        return "Estudiante agregado";
    }
    
    
}