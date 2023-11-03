package com.example.satoruquizzes.satoquiz.service;


import com.example.satoruquizzes.satoquiz.exception.NotFoundException;
import com.example.satoruquizzes.satoquiz.model.entity.Student;
import com.example.satoruquizzes.satoquiz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {
    @Autowired

    private StudentRepository studentRepository;
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getStudentByRegistrationDate(LocalDate registrationDate) {
        return studentRepository.findById(Long.valueOf(String.valueOf(registrationDate)))
                .orElseThrow(() -> new NotFoundException("Student not found for registration date: " + registrationDate));
    }

    public void delete(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}
