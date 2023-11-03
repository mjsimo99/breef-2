package com.example.satoruquizzes.satoquiz.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Validation")
@IdClass(Validation.ValidationId.class)
public class Validation {
    @Id
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Id
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    private double points;

    public static class ValidationId implements Serializable {
        private Long question;
        private Long answer;

    }
}
