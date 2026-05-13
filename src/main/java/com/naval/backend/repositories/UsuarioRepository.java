package com.naval.backend.repositories;

import com.naval.backend.models.Usuario;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
  Optional<Usuario> findByEmail(String email);

  boolean existsByEmail(String email);

  Optional<Usuario> findByResetToken(String resetToken);
}
