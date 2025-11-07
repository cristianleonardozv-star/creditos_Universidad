// repository/PersonaRepository.java
package com.previos.solicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.previos.solicitudes.entity.Persona;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
  Optional<Persona> findByDocumento(String documento);
}
