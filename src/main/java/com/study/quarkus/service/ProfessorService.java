package com.study.quarkus.service;

import com.study.quarkus.dto.ProfessorRequest;
import com.study.quarkus.dto.ProfessorResponse;
import com.study.quarkus.mapper.ProfessorMapper;
import com.study.quarkus.model.Professor;
import com.study.quarkus.repository.ProfessorRepository;

import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository repository;
    
    private final ProfessorMapper mapper;

    public List<ProfessorResponse> listProfessors() {
        return mapper.toResponse(repository.listAll());
    }

    public ProfessorResponse getProfessor(int professorId) {
        return mapper.toResponse(repository.findById(professorId));
    }

    @Transactional
    public ProfessorResponse saveProfessor(ProfessorRequest request) {
        Professor professor = mapper.toEntity(request);
        repository.persistAndFlush(professor);
        return mapper.toResponse(professor);
    }

    @Transactional
    public ProfessorResponse updateProfessor(int professorId, ProfessorRequest request) {
        Professor professor = repository.findById(professorId);
        if (professor == null) {
            throw new NotFoundException(String.format("Professor with ID %d not found", professorId));
        }
        repository.persistAndFlush(mapper.updateEntity(request, professor));
        return mapper.toResponse(professor);
    }

    @Transactional
    public void deleteProfessor(int professorId) {
        Optional<Professor> professor = repository.findByIdOptional(professorId);
        professor.ifPresent(repository::delete);
    }
}