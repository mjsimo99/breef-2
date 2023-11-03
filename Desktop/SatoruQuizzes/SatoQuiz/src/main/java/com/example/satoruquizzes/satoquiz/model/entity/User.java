package com.example.satoruquizzes.satoquiz.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@MappedSuperclass
@Table(name = "Users")
public abstract class User {
    @Column(name = "firstName")
    protected String firstName;
    @Column(name = "lastName")
    protected String lastName;
    @Column(name = "dateOfBirth")
    protected LocalDate dateOfBirth;
    @Column(name = "address")
    protected String address;

}
