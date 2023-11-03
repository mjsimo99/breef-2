package com.example.satoruquizzes.satoquiz.repository;

import com.example.satoruquizzes.satoquiz.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher , Long> {
}
