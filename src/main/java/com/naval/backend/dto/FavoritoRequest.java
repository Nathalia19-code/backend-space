package com.naval.backend.dto;

import java.util.Map;
import lombok.Data;

@Data
public class FavoritoRequest {
  private String tipo; // "vuelo", "hotel", "lugar"
  private Map<String, String> datos;
}
