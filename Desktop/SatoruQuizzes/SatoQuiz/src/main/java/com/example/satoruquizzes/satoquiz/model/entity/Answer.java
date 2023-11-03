package com.example.satoruquizzes.satoquiz.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private String answer;

    @OneToMany(mappedBy = "answer")
    private List<Validation> validations;
}
