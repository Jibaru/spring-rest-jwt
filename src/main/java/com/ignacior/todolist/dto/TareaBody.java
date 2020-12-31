package com.ignacior.todolist.dto;

import javax.validation.constraints.NotBlank;

public class TareaBody {

    @NotBlank
    private String contenido;

    @NotBlank
    private Long personaId;

    public TareaBody(String contenido, Long personaId) {
        this.contenido = contenido;
        this.personaId = personaId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

}
