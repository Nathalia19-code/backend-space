package com.naval.backend.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioUpdateRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
}
