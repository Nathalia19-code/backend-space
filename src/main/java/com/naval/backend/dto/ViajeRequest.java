package com.naval.backend.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ViajeRequest {
    private String titulo;
    private String destino;
    private LocalDate fechaSalida;
    private LocalDate fechaLlegada;
    private String portadaUrl;
    private boolean grupal;
    private List<String> colaboradores;
}