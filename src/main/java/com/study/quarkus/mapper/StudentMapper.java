package com.study.quarkus.mapper;

import com.study.quarkus.dto.StudentRequest;
import com.study.quarkus.dto.StudentResponse;
import com.study.quarkus.dto.TutorResponse;
import com.study.quarkus.model.Professor;
import com.study.quarkus.model.Student;

import javax.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class StudentMapper {
    public StudentResponse toResponse(Student student) {
        if (Objects.isNull(student))
            return null;
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        var response = StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .dateTime(formatter.format(student.getDateTime()))
                .build();
        if (Objects.nonNull(student.getTutor())) {
            response.setTutor(student.getTutor().getName());
        }
        return response;
    }

    public List<StudentResponse> toResponse(List<Student> response) {
        List<StudentResponse> studentsResponse = new ArrayList<StudentResponse>();
        response.stream()
                .forEach(a -> studentsResponse.add(toResponse(a)));
        return studentsResponse;
    }

    public Student toEntity(StudentRequest request) {
        return Student.builder()
                .name(request.getName())
                .build();
    }

    public Student updateEntity(StudentRequest request, Student entity) {
        entity.setName(request.getName());
        return entity;
    }

    public TutorResponse toResponse(Professor professor) {
        Objects.requireNonNull(professor, "entity must not be null");
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        return TutorResponse.builder()
                .tutor(professor.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
                .build();
    }
}