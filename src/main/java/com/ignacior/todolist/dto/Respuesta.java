package com.ignacior.todolist.dto;

public class Respuesta<T> {

    public boolean ok = true;
    public T datos;
    public String mensaje;

    public Respuesta(T datos, String mensaje) {
        this.datos = datos;
        this.mensaje = mensaje;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public T getDatos() {
        return datos;
    }

    public void setDatos(T datos) {
        this.datos = datos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
