package com.ignacior.todolist.dto;

public class Error {

    private String mensaje;

    public Error(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
