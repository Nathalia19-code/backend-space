package com.naval.backend.models;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "favoritos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorito {
  @Id private String id;
  private String usuarioId;
  private String tipo;
  private Map<String, String> datos;
  private LocalDateTime fechaGuardado;
}
