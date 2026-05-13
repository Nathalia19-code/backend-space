package com.naval.backend.services;

import com.naval.backend.dto.FavoritoRequest;
import com.naval.backend.dto.FavoritoResponse;
import com.naval.backend.models.Favorito;
import com.naval.backend.repositories.FavoritoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LugarService {

  @Autowired private FavoritoRepository favoritoRepository;

  public FavoritoResponse guardarFavorito(FavoritoRequest request, String usuarioId) {
    Favorito favorito = new Favorito();
    favorito.setUsuarioId(usuarioId);
    favorito.setTipo(request.getTipo());
    favorito.setDatos(request.getDatos());
    favorito.setFechaGuardado(LocalDateTime.now());
    return toResponse(favoritoRepository.save(favorito));
  }

  public List<FavoritoResponse> listarFavoritos(String usuarioId) {
    return favoritoRepository.findByUsuarioId(usuarioId).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public void eliminarFavorito(String id, String usuarioId) {
    Favorito favorito =
        favoritoRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Favorito no encontrado: " + id));
    if (!favorito.getUsuarioId().equals(usuarioId)) {
      throw new RuntimeException("No tienes permiso para eliminar este favorito");
    }
    favoritoRepository.deleteById(id);
  }

  private FavoritoResponse toResponse(Favorito f) {
    return new FavoritoResponse(
        f.getId(), f.getUsuarioId(), f.getTipo(), f.getDatos(), f.getFechaGuardado());
  }
}
