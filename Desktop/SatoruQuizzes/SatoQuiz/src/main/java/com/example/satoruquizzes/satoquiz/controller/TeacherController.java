package com.example.satoruquizzes.satoquiz.controller;

import com.example.satoruquizzes.satoquiz.model.entity.Teacher;
import com.example.satoruquizzes.satoquiz.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @PostMapping("/add")
    public Teacher addTeacher(@RequestBody Teacher teacher){
        return teacherService.save(teacher);
    }
    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAll();
    }
    @DeleteMapping("/{teacherId}")
    public void deleteTeacher(@PathVariable Long teacherId) {
        teacherService.delete(teacherId);
    }


}


