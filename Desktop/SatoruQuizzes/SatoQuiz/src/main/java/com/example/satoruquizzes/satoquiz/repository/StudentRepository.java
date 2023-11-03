package com.example.satoruquizzes.satoquiz.repository;


import com.example.satoruquizzes.satoquiz.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}