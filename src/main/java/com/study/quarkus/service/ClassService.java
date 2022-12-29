package com.study.quarkus.service;

import com.study.quarkus.dto.ClassRequest;
import com.study.quarkus.dto.ClassResponse;
import com.study.quarkus.dto.TitularResponse;
import com.study.quarkus.exception.InvalidStateException;
import com.study.quarkus.mapper.ClassMapper;
import com.study.quarkus.model.Class;
import com.study.quarkus.repository.ClassRepository;
import com.study.quarkus.repository.ProfessorRepository;

import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepository repository;

    private final ClassMapper mapper;
    
    private final ProfessorRepository professorRepository;

    public List<ClassResponse> listClasses() {
        return mapper.toResponse(repository.listAll());
    }

    public ClassResponse getClass(int classId) {
        return mapper.toResponse(repository.findById(classId));
    }

    @Transactional
    public ClassResponse saveClass(ClassRequest request) {
        Class classe = mapper.toEntity(request);
        repository.persistAndFlush(classe);
        return mapper.toResponse(classe);
    }

    @Transactional
    public ClassResponse updateClass(int classId, ClassRequest request) {
        Class classe = repository.findById(classId);
        if (classe == null) {
            throw new NotFoundException(String.format("Class with ID %d not found", classId));
        }
        repository.persistAndFlush(mapper.updateEntity(request, classe));
        return mapper.toResponse(classe);
    }

    @Transactional
    public void deleteClass(int classId) {
        Optional<Class> classe = repository.findByIdOptional(classId);
        classe.ifPresent(repository::delete);
    }

    @Transactional
    public TitularResponse updateTitular(int idClass, int idProfessor) {
        var classe = repository.findById(idClass);
        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(classe))
            throw new EntityNotFoundException("Class not found");
        if (Objects.isNull(professor))
            throw new EntityNotFoundException("Professor not found");
        if (repository.countTitularidadeByProfessor(professor) > 0) {
            throw new InvalidStateException("Professor must have at most one Class as titular");
        }
        classe.setTitular(professor);
        repository.persist(classe);
        return mapper.toResponse(professor);
    }

    public ClassResponse getClassByProfessorId(int idProfessor) {
        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(professor))
            throw new EntityNotFoundException("Professor not found");
        var query = repository.find("titular", professor);
        if (query.count() == 0)
            throw new EntityNotFoundException("Class not found");
        if (query.count() > 1)
            throw new InvalidStateException("Professor must have at most one Class as titular");
        var classe = query.singleResult();
        return mapper.toResponse(classe);
    }
}