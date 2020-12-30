/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignacior.todolist.security.controller;

import com.ignacior.todolist.dto.Respuesta;
import com.ignacior.todolist.dto.RespuestaError;
import com.ignacior.todolist.security.dto.NuevoUsuario;
import com.ignacior.todolist.security.entity.Rol;
import com.ignacior.todolist.security.entity.Usuario;
import com.ignacior.todolist.security.enums.RolNombre;
import com.ignacior.todolist.security.jwt.JwtProvider;
import com.ignacior.todolist.security.service.RolService;
import com.ignacior.todolist.security.service.UsuarioService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioController {

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> nuevo(
            @Valid @RequestBody NuevoUsuario nuevoUsuario,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(
                    new RespuestaError(
                            new com.ignacior.todolist.dto.Error(("Campos mal puestos"))),
                    HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return new ResponseEntity<>(
                    new RespuestaError(
                            new com.ignacior.todolist.dto.Error(("El email ya existe"))),
                    HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario(
                nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword())
        );

        Set<Rol> roles = new HashSet<>();

        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

        if (nuevoUsuario.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }

        usuario.setRoles(roles);

        usuarioService.save(usuario);

        return new ResponseEntity<>(
                new Respuesta<>(usuario, "Usuario guardado"),
                HttpStatus.OK);
    }
}
