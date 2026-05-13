package com.naval.backend.controllers;

import com.naval.backend.dto.BloqueRequest;
import com.naval.backend.dto.ViajeRequest;
import com.naval.backend.dto.ViajeResponse;
import com.naval.backend.services.ViajeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viajes")
public class ViajeController {

  @Autowired private ViajeService viajeService;

  // Crud de viajes

  @PostMapping
  public ResponseEntity<ViajeResponse> crear(@RequestBody ViajeRequest request) {
    return ResponseEntity.ok(viajeService.crear(request, getUsuarioId()));
  }

  @GetMapping
  public ResponseEntity<List<ViajeResponse>> listar() {
    return ResponseEntity.ok(viajeService.listarPorUsuario(getUsuarioId()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ViajeResponse> obtener(@PathVariable String id) {
    return ResponseEntity.ok(viajeService.obtenerPorId(id, getUsuarioId()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ViajeResponse> actualizar(
      @PathVariable String id, @RequestBody ViajeRequest request) {
    return ResponseEntity.ok(viajeService.actualizar(id, request, getUsuarioId()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable String id) {
    viajeService.eliminar(id, getUsuarioId());
    return ResponseEntity.noContent().build();
  }

  // Gestión del itinerario

  // Reemplaza todo el itinerario (el editor manda la lista completa ordenada)
  @PutMapping("/{id}/itinerario")
  public ResponseEntity<ViajeResponse> actualizarItinerario(
      @PathVariable String id, @RequestBody List<BloqueRequest> bloques) {
    return ResponseEntity.ok(viajeService.actualizarItinerario(id, bloques, getUsuarioId()));
  }

  // Añade un bloque nuevo al final
  @PostMapping("/{id}/itinerario/bloque")
  public ResponseEntity<ViajeResponse> agregarBloque(
      @PathVariable String id, @RequestBody BloqueRequest request) {
    return ResponseEntity.ok(viajeService.agregarBloque(id, request, getUsuarioId()));
  }

  // Edita el contenido de un bloque concreto
  @PutMapping("/{id}/itinerario/bloque/{bloqueId}")
  public ResponseEntity<ViajeResponse> actualizarBloque(
      @PathVariable String id, @PathVariable String bloqueId, @RequestBody BloqueRequest request) {
    return ResponseEntity.ok(viajeService.actualizarBloque(id, bloqueId, request, getUsuarioId()));
  }

  // Elimina un bloque concreto
  @DeleteMapping("/{id}/itinerario/bloque/{bloqueId}")
  public ResponseEntity<ViajeResponse> eliminarBloque(
      @PathVariable String id, @PathVariable String bloqueId) {
    return ResponseEntity.ok(viajeService.eliminarBloque(id, bloqueId, getUsuarioId()));
  }

  // Utilidad

  private String getUsuarioId() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
