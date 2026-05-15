package com.naval.backend.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PresenciaService {

  private final Map<String, String> usuarioPorSesion = new ConcurrentHashMap<>();
  private final Map<String, String> viajePorSesion = new ConcurrentHashMap<>();
  private final Map<String, Set<String>> sesionesPorViaje = new ConcurrentHashMap<>();

  public void registrar(String sessionId, String usuarioId, String viajeId) {
    usuarioPorSesion.put(sessionId, usuarioId);
    viajePorSesion.put(sessionId, viajeId);
    sesionesPorViaje.computeIfAbsent(viajeId, k -> ConcurrentHashMap.newKeySet()).add(sessionId);
  }

  public String getViajeIdPorSesion(String sessionId) {
    return viajePorSesion.get(sessionId);
  }

  public void desconectar(String sessionId) {
    String viajeId = viajePorSesion.remove(sessionId);
    usuarioPorSesion.remove(sessionId);
    if (viajeId != null) {
      Set<String> sesiones = sesionesPorViaje.get(viajeId);
      if (sesiones != null) {
        sesiones.remove(sessionId);
        if (sesiones.isEmpty()) {
          sesionesPorViaje.remove(viajeId);
        }
      }
    }
  }

  public List<String> obtenerUsuariosActivos(String viajeId) {
    Set<String> sesiones = sesionesPorViaje.getOrDefault(viajeId, Collections.emptySet());
    return sesiones.stream()
        .map(usuarioPorSesion::get)
        .filter(Objects::nonNull)
        .distinct()
        .collect(Collectors.toList());
  }
}
