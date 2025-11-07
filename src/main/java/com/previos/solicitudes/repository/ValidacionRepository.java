// repository/ValidacionRepository.java
package com.previos.solicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.previos.solicitudes.entity.Validacion;

import java.util.Optional;

public interface ValidacionRepository extends JpaRepository<Validacion, Integer> {
  Optional<Validacion> findByToken(String token);
  boolean existsByCodigo(String codigo);
}
