package com.naval.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoResponse {
    private String id;
    private String usuarioId;
    private String tipo;
    private Map<String, String> datos;
    private LocalDateTime fechaGuardado;
}