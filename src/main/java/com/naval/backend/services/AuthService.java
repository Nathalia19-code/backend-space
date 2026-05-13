package com.naval.backend.services;

import com.naval.backend.dto.LoginRequest;
import com.naval.backend.dto.LoginResponse;
import com.naval.backend.dto.RegisterRequest;
import com.naval.backend.models.Usuario;
import com.naval.backend.repositories.UsuarioRepository;
import com.naval.backend.security.JwtUtil;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

  @Autowired private UsuarioRepository usuarioRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private JavaMailSender mailSender;

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

    return new LoginResponse(
        token,
        guardado.getId(),
        guardado.getNombreUsuario(),
        guardado.getNombre(),
        guardado.getEmail());
  }

  public LoginResponse login(LoginRequest request) {
    Usuario usuario =
        usuarioRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Email o contraseña incorrectos"));

    if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
      throw new RuntimeException("Email o contraseña incorrectos");
    }

    String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getId());
    return new LoginResponse(
        token,
        usuario.getId(),
        usuario.getNombreUsuario(),
        usuario.getNombre(),
        usuario.getEmail());
  }

  public void forgotPassword(String email) {
    Usuario usuario =
        usuarioRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("No existe una cuenta con ese email"));

    String token = UUID.randomUUID().toString();
    usuario.setResetToken(token);
    usuario.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30));
    usuarioRepository.save(usuario);

    SimpleMailMessage mensaje = new SimpleMailMessage();
    mensaje.setTo(email);
    mensaje.setSubject("Recuperar contraseña — Naval");
    mensaje.setText(
        "Hola "
            + usuario.getNombre()
            + ",\n\n"
            + "Para restablecer tu contraseña haz clic en este enlace:\n\n"
            + "http://localhost:5173/reset-password?token="
            + token
            + "\n\n"
            + "El enlace caduca en 30 minutos.\n\n"
            + "Si no solicitaste este cambio, ignora este email.");
    mailSender.send(mensaje);
  }

  public void resetPassword(String token, String nuevaPassword) {
    Usuario usuario =
        usuarioRepository
            .findByResetToken(token)
            .orElseThrow(() -> new RuntimeException("El enlace no es válido o ya fue utilizado"));

    if (LocalDateTime.now().isAfter(usuario.getResetTokenExpiry())) {
      throw new RuntimeException("El enlace ha caducado, solicita uno nuevo");
    }

    usuario.setPassword(passwordEncoder.encode(nuevaPassword));
    usuario.setResetToken(null);
    usuario.setResetTokenExpiry(null);
    usuarioRepository.save(usuario);
  }

  public LoginResponse loginConGoogle(String accessToken) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    Map<?, ?> userInfo;
    try {
      userInfo =
          restTemplate
              .exchange(
                  "https://www.googleapis.com/oauth2/v3/userinfo",
                  HttpMethod.GET,
                  entity,
                  Map.class)
              .getBody();
    } catch (Exception e) {
      throw new RuntimeException("Token de Google inválido");
    }

    String email = (String) userInfo.get("email");
    String nombre = (String) userInfo.get("given_name");
    String apellido = (String) userInfo.get("family_name");

    Usuario usuario =
        usuarioRepository
            .findByEmail(email)
            .orElseGet(
                () -> {
                  Usuario nuevo = new Usuario();
                  nuevo.setNombreUsuario(email.split("@")[0]);
                  nuevo.setNombre(nombre != null ? nombre : "");
                  nuevo.setApellido(apellido != null ? apellido : "");
                  nuevo.setEmail(email);
                  nuevo.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                  nuevo.setFechaRegistro(LocalDateTime.now());
                  nuevo.setActivo(true);
                  nuevo.setRol("USER");
                  return usuarioRepository.save(nuevo);
                });

    String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getId());
    return new LoginResponse(
        token,
        usuario.getId(),
        usuario.getNombreUsuario(),
        usuario.getNombre(),
        usuario.getEmail());
  }
}
