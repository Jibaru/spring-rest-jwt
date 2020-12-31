package com.ignacior.todolist.controller;

import com.ignacior.todolist.dto.Error;
import com.ignacior.todolist.dto.Respuesta;
import com.ignacior.todolist.dto.RespuestaError;
import com.ignacior.todolist.dto.TareaBody;
import com.ignacior.todolist.entity.Persona;
import com.ignacior.todolist.entity.Tarea;
import com.ignacior.todolist.message.TareaMessages;
import com.ignacior.todolist.service.PersonaService;
import com.ignacior.todolist.service.TareaService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareaController {

    @Autowired
    TareaService tareaService;

    @Autowired
    PersonaService personaService;

    @Autowired
    TareaMessages tareaMessages;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Respuesta> getAll() {
        List<Tarea> lista = tareaService.getAll();
        return new ResponseEntity<>(
                new Respuesta<>(lista, tareaMessages.getAll()),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOnebyId(@PathVariable("id") long id) {
        if (!tareaService.existsById(id)) {

            return new ResponseEntity<>(
                    new RespuestaError(
                            new Error((tareaMessages.getNotFound()))),
                    HttpStatus.NOT_FOUND);
        }

        Tarea tarea = tareaService.getOneById(id).get();

        return new ResponseEntity<>(
                new Respuesta<>(tarea, tareaMessages.getFound()),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        if (!tareaService.existsById(id)) {

            return new ResponseEntity<>(
                    new RespuestaError(
                            new Error((tareaMessages.getNotFound()))),
                    HttpStatus.NOT_FOUND);
        }
        Tarea tarea = tareaService.getOneById(id).get();
        tareaService.delete(tarea);

        return new ResponseEntity<>(
                new Respuesta<>(tarea, tareaMessages.getDelete()),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/terminada/{id}")
    public ResponseEntity<?> updateTerminada(
            @PathVariable("id") long id,
            @RequestBody String terminada
    ) {
        List<Error> errores = new ArrayList<>();

        if (!tareaService.existsById(id)) {
            errores.add(new Error(tareaMessages.getNotFound()));
        }

        if (terminada == null) {
            errores.add(new Error("El campo 'terminado' es necesario"));
        }

        if (!errores.isEmpty()) {

            return new ResponseEntity<>(
                    new RespuestaError(errores),
                    HttpStatus.BAD_REQUEST);
        }

        Tarea tarea = tareaService.getOneById(id).get();
        if (terminada.equals("true")) {
            tarea.setTerminada(true);
        } else {
            tarea.setTerminada(false);
        }
        tarea.setTerminada(true);
        tareaService.save(tarea);

        return new ResponseEntity<>(
                new Respuesta<>(tarea, "Tarea terminada"),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody TareaBody tareaBody,
            BindingResult bindingResult
    ) {
        List<Error> errores = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(
                    new RespuestaError(
                            new com.ignacior.todolist.dto.Error(("Campos mal puestos"))),
                    HttpStatus.BAD_REQUEST);
        }

        if (!personaService.existsById(tareaBody.getPersonaId())) {
            errores.add(new Error("La persona no existe"));
        }

        if (!errores.isEmpty()) {
            return new ResponseEntity<>(
                    new RespuestaError(errores),
                    HttpStatus.BAD_REQUEST);
        }

        Persona persona = personaService.getOneById(tareaBody.getPersonaId()).get();
        Tarea tarea = new Tarea(tareaBody.getContenido(), false, persona);
        tareaService.save(tarea);

        return new ResponseEntity<>(
                new Respuesta<>(tarea, "Tarea creada"),
                HttpStatus.OK);
    }
}
