package com.study.quarkus.service;

import com.study.quarkus.dto.StudentRequest;
import com.study.quarkus.dto.StudentResponse;
import com.study.quarkus.dto.TutorResponse;
import com.study.quarkus.mapper.StudentMapper;
import com.study.quarkus.model.Student;
import com.study.quarkus.repository.ProfessorRepository;
import com.study.quarkus.repository.StudentRepository;

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
public class StudentService {
    private final StudentRepository repository;

    private final StudentMapper mapper;

    private final ProfessorRepository professorRepository;

    public List<StudentResponse> listStudents() {
        return mapper.toResponse(repository.listAll());
    }

    public StudentResponse getStudent(int studentId) {
        return mapper.toResponse(repository.findById(studentId));
    }

    @Transactional
    public StudentResponse saveStudent(StudentRequest request) {
        Student student = mapper.toEntity(request);
        repository.persistAndFlush(student);
        return mapper.toResponse(student);
    }

    @Transactional
    public StudentResponse updateStudent(int studentId, StudentRequest request) {
        Student student = repository.findById(studentId);
        if (student == null) {
            throw new NotFoundException(String.format("Student with ID %d not found", studentId));
        }
        repository.persistAndFlush(mapper.updateEntity(request, student));
        return mapper.toResponse(student);
    }

    @Transactional
    public void deleteStudent(int studentId) {
        Optional<Student> student = repository.findByIdOptional(studentId);
        student.ifPresent(repository::delete);
    }

    @Transactional
    public TutorResponse updateTutor(int idStudent, int idProfessor) {
        var student = repository.findById(idStudent);
        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(student))
            throw new EntityNotFoundException("Student not found");
        if (Objects.isNull(professor))
            throw new EntityNotFoundException("Professor not found");
        student.setTutor(professor);
        repository.persist(student);
        return mapper.toResponse(professor);
    }

    public List<StudentResponse> getTutoredByProfessorId(int idProfessor) {
        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(professor))
            throw new EntityNotFoundException("Professor not found");
        List<Student> listOfEntities = repository.getTutoredByProfessor(professor);
        return mapper.toResponse(listOfEntities);
    }
}