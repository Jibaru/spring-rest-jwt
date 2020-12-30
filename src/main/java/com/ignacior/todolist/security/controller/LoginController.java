package com.ignacior.todolist.security.controller;

import com.ignacior.todolist.dto.Error;
import com.ignacior.todolist.dto.RespuestaError;
import com.ignacior.todolist.security.dto.JwtDTO;
import com.ignacior.todolist.security.dto.LoginUsuario;
import com.ignacior.todolist.security.jwt.JwtProvider;
import com.ignacior.todolist.security.service.RolService;
import com.ignacior.todolist.security.service.UsuarioService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class LoginController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginUsuario loginUsuario,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(
                    new RespuestaError(
                            new Error(("Campos mal puestos"))),
                    HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginUsuario.getEmail(),
                                loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generaToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtDTO jwtDTO = new JwtDTO(
                jwt,
                userDetails.getUsername(),
                userDetails.getAuthorities());

        return new ResponseEntity(jwtDTO, HttpStatus.OK);
    }

}
