package com.ignacior.todolist.controller;

import com.ignacior.todolist.dto.Error;
import com.ignacior.todolist.dto.PersonaQuery;
import com.ignacior.todolist.dto.Respuesta;
import com.ignacior.todolist.dto.RespuestaError;
import com.ignacior.todolist.entity.Persona;
import com.ignacior.todolist.message.PersonaMessages;
import com.ignacior.todolist.service.PersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaController {

    @Autowired
    PersonaService personaService;

    @Autowired
    PersonaMessages personaMessages;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Respuesta> getAll(PersonaQuery personaQuery) {
        List<Persona> lista = personaService.getAll(personaQuery);
        return new ResponseEntity<>(
                new Respuesta<>(lista, personaMessages.getAll()),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOnebyId(@PathVariable("id") long id) {
        if (!personaService.existsById(id)) {

            return new ResponseEntity<>(
                    new RespuestaError(
                            new Error((personaMessages.getNotFound()))),
                    HttpStatus.NOT_FOUND);
        }

        Persona persona = personaService.getOneById(id).get();

        return new ResponseEntity<>(
                new Respuesta<>(persona, personaMessages.getFound()),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        if (!personaService.existsById(id)) {

            return new ResponseEntity<>(
                    new RespuestaError(
                            new Error((personaMessages.getNotFound()))),
                    HttpStatus.NOT_FOUND);
        }
        Persona persona = personaService.getOneById(id).get();
        personaService.delete(persona);

        return new ResponseEntity<>(
                new Respuesta<>(persona, personaMessages.getDelete()),
                HttpStatus.OK);
    }
}
