package com.ignacior.todolist.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignacior.todolist.dto.Error;
import com.ignacior.todolist.dto.RespuestaError;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException e)
            throws IOException, ServletException {
        logger.error("Fail en el método commnence auth");

        ServletOutputStream out = res.getOutputStream();
        res.setContentType("application/json");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();

        RespuestaError respError = new RespuestaError(
                new Error("Email o password inválidos"));
        String jsonString = mapper.writeValueAsString(respError);

        out.print(jsonString);
    }

}
