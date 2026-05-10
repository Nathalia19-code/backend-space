package com.naval.backend.services;

import com.naval.backend.dto.UsuarioResponse;
import com.naval.backend.dto.UsuarioUpdateRequest;
import com.naval.backend.models.Usuario;
import com.naval.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponse obtenerPerfil(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        return toResponse(usuario);
    }

    public UsuarioResponse actualizar(String id, UsuarioUpdateRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));

        if (request.getNombre() != null) usuario.setNombre(request.getNombre());
        if (request.getApellido() != null) usuario.setApellido(request.getApellido());
        if (request.getEmail() != null) usuario.setEmail(request.getEmail());
        if (request.getTelefono() != null) usuario.setTelefono(request.getTelefono());
        if (request.getFechaNacimiento() != null) usuario.setFechaNacimiento(request.getFechaNacimiento());

        return toResponse(usuarioRepository.save(usuario));
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(
                u.getId(), u.getNombreUsuario(), u.getNombre(), u.getApellido(),
                u.getEmail(), u.getTelefono(), u.getFechaNacimiento(),
                u.getFechaRegistro(), u.isActivo(), u.getRol()
        );
    }
}
