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
@Table(name = "CLASSES_Rafael")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer id;
    

    @Column(name = "class_name", nullable = false)
    @NotBlank(message = "Class' name cannot be null")
    private String name;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dateTime;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular", unique = true)
    private Professor titular;

    @PrePersist
    public void prePersist() {
        setDateTime(LocalDateTime.now());
    }
}