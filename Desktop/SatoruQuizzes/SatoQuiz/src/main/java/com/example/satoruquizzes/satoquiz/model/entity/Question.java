package com.example.satoruquizzes.satoquiz.model.entity;

import com.example.satoruquizzes.satoquiz.model.enums.TypeAnswer;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "questionId")
    private Long questionId;

    @Column(name = "answersNumber")
    private int answersNumber;

    @Column(name = "answersNumberCorrect")
    private int answersNumberCorrect;

    private String text;

    @Column(name = "duration")
    private Time duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeAnswer type;

    @Column(name = "scorePoints")
    private double scorePoints;

    @ManyToOne
    @JoinColumn(name = "subject")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "level")
    private Level level;

    @OneToMany(mappedBy = "question")
    private List<Validation> validations;
}