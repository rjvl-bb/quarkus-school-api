package com.study.quarkus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STUDENTS_Rafael")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer id;

    @Column(name = "student_name", nullable = false)
    @NotBlank(message = "Student's name cannot be null")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor")
    private Professor tutor;
    
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dateTime;

    @PrePersist
    public void prePersist() {
        setDateTime(LocalDateTime.now());
    }
}