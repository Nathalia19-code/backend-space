package com.naval.backend.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RegisterRequest {
  private String nombreUsuario;
  private String nombre;
  private String apellido;
  private String email;
  private String password;
  private String telefono;
  private LocalDate fechaNacimiento;
}
