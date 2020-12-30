package com.ignacior.todolist.dto;

import java.util.Arrays;
import java.util.List;

public class RespuestaError {

    private boolean ok = false;
    private List<Error> errores;

    public RespuestaError(Error... errores) {
        this.errores = Arrays.asList(errores);
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<Error> getErrores() {
        return errores;
    }

    public void setErrores(List<Error> errores) {
        this.errores = errores;
    }

}
