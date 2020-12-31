package com.ignacior.todolist.service;

import com.ignacior.todolist.entity.Tarea;
import com.ignacior.todolist.repository.TareaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TareaService {

    @Autowired
    TareaRepository tareaRepository;

    public Optional<Tarea> getOneById(long id) {
        return tareaRepository.findById(id);
    }

    public List<Tarea> getAll() {
        return tareaRepository.findAll();
    }

    public void save(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    public void delete(Tarea tarea) {
        tareaRepository.delete(tarea);
    }

    public boolean existsById(long id) {
        return tareaRepository.existsById(id);
    }
}
