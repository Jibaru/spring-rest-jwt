package com.ignacior.todolist.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignacior.todolist.dto.RespuestaError;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest req,
            HttpServletResponse res,
            AccessDeniedException e
    ) throws IOException, ServletException {
        ServletOutputStream out = res.getOutputStream();
        res.setContentType("application/json");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();

        RespuestaError respError = new RespuestaError(
                new com.ignacior.todolist.dto.Error("Credenciales inválidos"));
        String jsonString = mapper.writeValueAsString(respError);

        out.print(jsonString);
    }

}
