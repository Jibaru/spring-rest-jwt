package com.ignacior.todolist.specification;

import com.ignacior.todolist.entity.Persona;
import org.springframework.data.jpa.domain.Specification;

public abstract class PersonaSpecifications {

    public static Specification<Persona> nombreContains(String nombre) {
        return (persona, cq, cb)
                -> cb.like(persona.get("nombre"), "%" + nombre + "%");
    }

    public static Specification<Persona> dniContains(String dni) {
        return (persona, cq, cb)
                -> cb.like(persona.get("dni"), "%" + dni + "%");
    }

}
