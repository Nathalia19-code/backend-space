package com.naval.backend.dto;

import com.naval.backend.models.BloqueItinerario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViajeResponse {
    private String id;
    private String titulo;
    private String destino;
    private LocalDate fechaSalida;
    private LocalDate fechaLlegada;
    private String portadaUrl;
    private String propietarioId;
    private boolean grupal;
    private List<String> colaboradores;
    private List<BloqueItinerario> itinerario;
}