package com.ignacior.todolist.security.controller;

import com.ignacior.todolist.dto.Error;
import com.ignacior.todolist.dto.Respuesta;
import com.ignacior.todolist.dto.RespuestaError;
import com.ignacior.todolist.entity.Persona;
import com.ignacior.todolist.security.dto.RegistroBody;
import com.ignacior.todolist.security.entity.Rol;
import com.ignacior.todolist.security.entity.Usuario;
import com.ignacior.todolist.security.enums.RolNombre;
import com.ignacior.todolist.security.jwt.JwtProvider;
import com.ignacior.todolist.security.service.RolService;
import com.ignacior.todolist.security.service.UsuarioService;
import com.ignacior.todolist.service.PersonaService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/registro")
@CrossOrigin
public class RegistroController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PersonaService personaService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<?> nuevo(
            @Valid @RequestBody RegistroBody registroBody,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(
                    new RespuestaError(
                            new Error(("Campos mal puestos"))),
                    HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.existsByEmail(registroBody.getEmail())) {
            return new ResponseEntity<>(
                    new RespuestaError(
                            new Error(("El email ya existe"))),
                    HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = buildUsuario(registroBody);
        Persona persona = buildPersona(registroBody);

        usuarioService.save(usuario);
        persona.setUsuario(usuario);
        personaService.save(persona);

        return new ResponseEntity<>(
                new Respuesta<>(usuario, "Usuario guardado"),
                HttpStatus.OK);
    }

    private Usuario buildUsuario(RegistroBody registroBody) {
        Usuario usuario = new Usuario(
                registroBody.getEmail(),
                passwordEncoder.encode(registroBody.getPassword())
        );

        Set<Rol> roles = new HashSet<>();

        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

        usuario.setRoles(roles);

        return usuario;
    }

    private Persona buildPersona(RegistroBody registroBody) {
        Persona persona = new Persona(
                registroBody.getNombre(),
                registroBody.getDni());

        return persona;
    }

}
