package com.naval.backend.services;

import com.naval.backend.dto.LoginRequest;
import com.naval.backend.dto.LoginResponse;
import com.naval.backend.dto.RegisterRequest;
import com.naval.backend.models.Usuario;
import com.naval.backend.repositories.UsuarioRepository;
import com.naval.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        usuario.setRol("USER");

        Usuario guardado = usuarioRepository.save(usuario);
        String token = jwtUtil.generarToken(guardado.getEmail(), guardado.getId());

        return new LoginResponse(token, guardado.getId(), guardado.getNombreUsuario(), guardado.getNombre(), guardado.getEmail());
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getId());
        return new LoginResponse(token, usuario.getId(), usuario.getNombreUsuario(), usuario.getNombre(), usuario.getEmail());
    }
}
