package com.example.satoruquizzes.satoquiz.service;

import com.example.satoruquizzes.satoquiz.exception.NotFoundException;
import com.example.satoruquizzes.satoquiz.model.entity.Teacher;
import com.example.satoruquizzes.satoquiz.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    public Teacher save(Teacher teacher){
        return teacherRepository.save(teacher);
    }
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherBySpecialty(String specialty) {
        return teacherRepository.findById(Long.valueOf(specialty))
                .orElseThrow(() -> new NotFoundException("Teacher not found for specialty : " + specialty));
    }

    public void delete(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}
