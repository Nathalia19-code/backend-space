package com.naval.backend.repositories;

import com.naval.backend.models.Favorito;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends MongoRepository<Favorito, String> {
  // Para listar los favoritos de un usuario concreto
  List<Favorito> findByUsuarioId(String usuarioId);
}
