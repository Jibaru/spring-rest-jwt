package com.ignacior.todolist.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class PersonaMessages {

    @Value("${persona.found.message}")
    private String found;

    @Value("${persona.notfound.message}")
    private String notFound;

    @Value("${persona.all.message}")
    private String all;

    @Value("${persona.delete.message}")
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
