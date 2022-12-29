package com.study.quarkus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROFESSORS_Rafael")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Integer id;

    @Column(name = "professor_name", nullable = false)
    @NotBlank(message = "Professor's name cannot be null")
    private String name;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dateTime;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "titular")
    private Class classe;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutor")
    private List<Student> students;
    
    @PrePersist
    public void prePersist() {
        setDateTime(LocalDateTime.now());
    }
}