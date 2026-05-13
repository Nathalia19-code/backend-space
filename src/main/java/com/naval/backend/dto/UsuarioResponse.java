package com.naval.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
  private String id;
  private String nombreUsuario;
  private String nombre;
  private String apellido;
  private String email;
  private String telefono;
  private LocalDate fechaNacimiento;
  private LocalDateTime fechaRegistro;
  private boolean activo;
  private String rol;
}
