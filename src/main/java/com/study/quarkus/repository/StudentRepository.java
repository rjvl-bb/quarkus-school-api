package com.study.quarkus.repository;

import com.study.quarkus.model.Professor;
import com.study.quarkus.model.Student;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentRepository implements PanacheRepositoryBase<Student, Integer> {
    public List<Student> getTutoredByProfessor(Professor professor) {
        Objects.requireNonNull(professor, "Professor must be not null");
        var query = find("tutor", Sort.ascending("name"), professor);
        return query.list();
    }
}