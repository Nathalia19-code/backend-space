package com.naval.backend.controllers;

import com.naval.backend.dto.UsuarioResponse;
import com.naval.backend.dto.UsuarioUpdateRequest;
import com.naval.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> obtenerPerfil() {
        return ResponseEntity.ok(usuarioService.obtenerPerfil(getUsuarioId()));
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> actualizar(@RequestBody UsuarioUpdateRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(getUsuarioId(), request));
    }

    private String getUsuarioId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
