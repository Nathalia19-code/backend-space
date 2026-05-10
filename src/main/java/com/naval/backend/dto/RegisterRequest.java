package com.naval.backend.dto;

import lombok.Data;
import java.time.LocalDate;

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
