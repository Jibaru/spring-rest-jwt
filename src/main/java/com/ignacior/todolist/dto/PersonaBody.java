package com.ignacior.todolist.dto;

public class PersonaBody {

    private String nombre;

    public PersonaBody(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
