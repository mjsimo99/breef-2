package com.example.satoruquizzes.satoquiz.service;

import com.example.satoruquizzes.satoquiz.exception.NotFoundException;
import com.example.satoruquizzes.satoquiz.model.entity.Subject;
import com.example.satoruquizzes.satoquiz.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject not found for ID: " + subjectId));
    }

    public void delete(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}
