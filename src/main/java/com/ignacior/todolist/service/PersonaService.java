package com.ignacior.todolist.service;

import com.ignacior.todolist.dto.PersonaQuery;
import com.ignacior.todolist.entity.Persona;
import com.ignacior.todolist.repository.PersonaRepository;
import com.ignacior.todolist.specification.PersonaSpecifications;
import io.github.jhipster.service.QueryService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonaService extends QueryService<Persona> {

    @Autowired
    private PersonaRepository personaRepository;

    public Optional<Persona> getOneById(long id) {
        return personaRepository.findById(id);
    }

    public List<Persona> getAll(PersonaQuery params) {
        return personaRepository.findAll(createSpecification(params));
    }

    public void save(Persona persona) {
        personaRepository.save(persona);
    }

    public void update(Persona persona) {

    }

    public void delete(Persona persona) {
        personaRepository.delete(persona);
    }

    public boolean existsById(long id) {
        return personaRepository.existsById(id);
    }

    private Specification<Persona> createSpecification(PersonaQuery criteria) {
        Specification<Persona> specification = Specification.where(null);

        if (criteria != null) {
            if (criteria.getNombre() != null) {
                specification
                        = specification.and(
                                PersonaSpecifications.nombreContains(criteria.getNombre()));
            }

            if (criteria.getDni() != null) {
                specification
                        = specification.and(
                                PersonaSpecifications.dniContains(criteria.getDni()));
            }
        }

        return specification;
    }

}
