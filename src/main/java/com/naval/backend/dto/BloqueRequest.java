package com.naval.backend.dto;

import java.util.Map;
import lombok.Data;

@Data
public class BloqueRequest {
  private String tipo; // "texto", "vuelo", "hotel", "lugar"
  private String contenido; // texto libre para bloques de tipo texto
  private Map<String, String> dato; // datos estructurados para vuelo/hotel/lugar
}
