// entity/Solicitud.java
package com.previos.solicitudes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "solicitud")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Solicitud {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private LocalDate fecha;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "solicitante_id", nullable = false)
  private Persona solicitante;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "codeudor_id", nullable = false)
  private Persona codeudor;

  @Column(precision = 10, scale = 0)
  private BigDecimal valor; // opcional si no te lo envían

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estado_id", nullable = false)
  private Estado estado;

  @Column(name = "observacion", columnDefinition = "TEXT")
  private String observacion;

  @Column(name = "codigo_radicado", length = 10, nullable = false, unique = true)
  private String codigoRadicado;
}
