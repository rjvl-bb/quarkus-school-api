package com.study.quarkus.repository;

import com.study.quarkus.model.Class;
import com.study.quarkus.model.Professor;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClassRepository implements PanacheRepositoryBase<Class, Integer> {
    public long countTitularidadeByProfessor(Professor professor) {
        var query = find("titular", professor);
        return query.count();
    }
}