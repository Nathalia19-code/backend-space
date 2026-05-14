package com.naval.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresenciaMessage {
  private String viajeId;
  private List<String> usuariosActivos;
}
