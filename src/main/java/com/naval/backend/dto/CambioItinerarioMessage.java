package com.naval.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambioItinerarioMessage {
  private String viajeId;
  private String origen;
  private String update;
}
