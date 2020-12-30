package com.ignacior.todolist.repository;

import com.ignacior.todolist.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>,
        JpaSpecificationExecutor<Persona> {

}
