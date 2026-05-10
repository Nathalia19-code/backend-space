package com.naval.backend.dto;

import lombok.Data;
import java.util.Map;

@Data
public class FavoritoRequest {
    private String tipo; // "vuelo", "hotel", "lugar"
    private Map<String, String> datos;
}