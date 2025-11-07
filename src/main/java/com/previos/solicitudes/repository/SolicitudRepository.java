// repository/SolicitudRepository.java
package com.previos.solicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.previos.solicitudes.entity.Solicitud;
import com.previos.solicitudes.entity.Persona;
import com.previos.solicitudes.entity.Estado;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
  List<Solicitud> findBySolicitanteAndEstado_DescripcionIn(Persona solicitante, List<String> estados);
  boolean existsByCodigoRadicado(String codigoRadicado);
}
