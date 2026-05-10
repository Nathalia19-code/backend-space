package com.naval.backend.repositories;

import com.naval.backend.models.Favorito;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FavoritoRepository extends MongoRepository<Favorito, String> {
    // Para listar los favoritos de un usuario concreto
    List<Favorito> findByUsuarioId(String usuarioId);
}