package com.ignacior.todolist.controller;

import com.ignacior.todolist.dto.Error;
import com.ignacior.todolist.dto.Respuesta;
import com.ignacior.todolist.dto.RespuestaError;
import com.ignacior.todolist.entity.Tarea;
import com.ignacior.todolist.message.TareaMessages;
import com.ignacior.todolist.service.TareaService;
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
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareaController {

    @Autowired
    TareaService tareaService;

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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        if (!tareaService.existsById(id)) {

            return new ResponseEntity<>(
                    new RespuestaError(
                            new Error((tareaMessages.getFound()))),
                    HttpStatus.NOT_FOUND);
        }
        Tarea tarea = tareaService.getOneById(id).get();
        tareaService.delete(tarea);

        return new ResponseEntity<>(
                new Respuesta<>(tarea, tareaMessages.getDelete()),
                HttpStatus.OK);
    }
}
