package com.naval.backend.models;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "viajes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {
  @Id private String id;
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
