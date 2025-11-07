// repository/EstadoRepository.java
package com.previos.solicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.previos.solicitudes.entity.Estado;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
  Optional<Estado> findByDescripcion(String descripcion);
}
