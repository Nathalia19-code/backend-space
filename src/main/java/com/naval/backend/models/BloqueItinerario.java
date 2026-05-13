package com.naval.backend.models;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloqueItinerario {
  @Id private String id;
  private String tipo;
  private String contenido;
  private int orden;
  private Map<String, String> dato;
}
