package com.example.satoruquizzes.satoquiz.controller;


import com.example.satoruquizzes.satoquiz.model.entity.Student;
import com.example.satoruquizzes.satoquiz.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/students")

public class StudentController {
    @Autowired
    private StudentService studentService;



    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GetMapping("/{registrationDate}")
    public Student getStudentByRegistrationDate(@PathVariable String registrationDate) {
        LocalDate date = LocalDate.parse(registrationDate);
        return studentService.getStudentByRegistrationDate(date);
    }

    @DeleteMapping("/{studentid}")
    public void deleteStudent(@PathVariable Long studentid) {
        studentService.delete(studentid);
    }
}
