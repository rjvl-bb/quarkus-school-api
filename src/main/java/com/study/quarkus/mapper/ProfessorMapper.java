package com.study.quarkus.mapper;

import com.study.quarkus.dto.ProfessorRequest;
import com.study.quarkus.dto.ProfessorResponse;
import com.study.quarkus.model.Professor;

import javax.enterprise.context.RequestScoped;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestScoped
public class ProfessorMapper {
    public ProfessorResponse toResponse(Professor professor) {
        if (Objects.isNull(professor))
            return null;
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        return ProfessorResponse.builder()
                .id(professor.getId())
                .name(professor.getName())
                .dateTime(formatter.format(professor.getDateTime()))
                .build();
    }

    public List<ProfessorResponse> toResponse(List<Professor> response) {
        List<ProfessorResponse> professorsResponse = new ArrayList<ProfessorResponse>();
        response.stream()
                .forEach(a -> professorsResponse.add(toResponse(a)));
        return professorsResponse;
    }

    public Professor toEntity(ProfessorRequest request) {
        return Professor.builder()
                .name(request.getName())
                .build();
    }

    public Professor updateEntity(ProfessorRequest request, Professor entity) {
        entity.setName(request.getName());
        return entity;
    }
}