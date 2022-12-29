package com.study.quarkus.mapper;

import com.study.quarkus.dto.ClassRequest;
import com.study.quarkus.dto.ClassResponse;
import com.study.quarkus.dto.TitularResponse;
import com.study.quarkus.model.Class;
import com.study.quarkus.model.Professor;

import javax.enterprise.context.RequestScoped;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestScoped
public class ClassMapper {
    public ClassResponse toResponse(Class classe) {
        if (Objects.isNull(classe))
            return null;
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        var response = ClassResponse.builder()
                .id(classe.getId())
                .name(classe.getName())
                .dateTime(formatter.format(classe.getDateTime()))
                .build();
        if (Objects.nonNull(classe.getTitular())) {
            response.setTitular(classe.getTitular().getName());
        }
        return response;
    }

    public List<ClassResponse> toResponse(List<Class> response) {
        List<ClassResponse> classesResponse = new ArrayList<ClassResponse>();
        response.stream()
                .forEach(a -> classesResponse.add(toResponse(a)));
        return classesResponse;
    }

    public Class toEntity(ClassRequest request) {
        return Class.builder()
                .name(request.getName())
                .build();
    }

    public Class updateEntity(ClassRequest request, Class entity) {
        entity.setName(request.getName());
        return entity;
    }

    public TitularResponse toResponse(Professor classesResponse) {
        if (Objects.isNull(classesResponse))
            return null;
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        return TitularResponse.builder()
                .titular(classesResponse.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
                .build();
    }
}