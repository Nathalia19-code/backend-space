package com.naval.backend.repositories;

import com.naval.backend.models.Viaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViajeRepository extends MongoRepository<Viaje, String> {
    // Busca los viajes donde el propietarioId coincide con el del usuario
    List<Viaje> findByPropietarioId(String propietarioId);

    // Para buscar viajes donde el usuario es colaborador:
    List<Viaje> findByColaboradoresContaining(String usuarioId);
}