package com.example.satoruquizzes.satoquiz.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "label")
    private String label;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Subject parent;

    @OneToMany(mappedBy = "parent")
    private List<Subject> children;
}
