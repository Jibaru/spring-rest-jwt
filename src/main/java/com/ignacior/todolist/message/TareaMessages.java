package com.ignacior.todolist.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TareaMessages {

    @Value("${tarea.found.message}")
    private String found;

    @Value("${tarea.notfound.message}")
    private String notFound;

    @Value("${tarea.all.message}")
    private String all;

    @Value("${tarea.delete.message}")
    private String delete;

    public String getFound() {
        return found;
    }

    public String getNotFound() {
        return notFound;
    }

    public String getAll() {
        return all;
    }

    public String getDelete() {
        return delete;
    }

}
