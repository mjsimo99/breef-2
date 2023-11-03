package com.example.satoruquizzes.satoquiz.controller;

import com.example.satoruquizzes.satoquiz.model.entity.Subject;
import com.example.satoruquizzes.satoquiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/add")
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectService.save(subject);
    }

    @GetMapping("/all")
    public List<Subject> getAllSubjects() {
        return subjectService.getAll();
    }

    @GetMapping("/{subjectId}")
    public Subject getSubjectById(@PathVariable Long subjectId) {
        return subjectService.getSubjectById(subjectId);
    }

    @DeleteMapping("/{subjectId}")
    public void deleteSubject(@PathVariable Long subjectId) {
        subjectService.delete(subjectId);
    }
}
