package com.naval.backend.controllers;

import com.naval.backend.dto.FavoritoRequest;
import com.naval.backend.dto.FavoritoResponse;
import com.naval.backend.services.LugarService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favoritos")
public class LugarController {

  @Autowired private LugarService lugarService;

  @PostMapping
  public ResponseEntity<FavoritoResponse> guardar(@RequestBody FavoritoRequest request) {
    return ResponseEntity.ok(lugarService.guardarFavorito(request, getUsuarioId()));
  }

  @GetMapping
  public ResponseEntity<List<FavoritoResponse>> listar() {
    return ResponseEntity.ok(lugarService.listarFavoritos(getUsuarioId()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable String id) {
    lugarService.eliminarFavorito(id);
    return ResponseEntity.noContent().build();
  }

  private String getUsuarioId() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
