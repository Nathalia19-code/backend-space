package com.naval.backend.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioUpdateRequest {
  private String nombre;
  private String apellido;
  private String email;
  private String telefono;
  private LocalDate fechaNacimiento;
}
