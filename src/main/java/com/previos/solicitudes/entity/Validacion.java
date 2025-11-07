// entity/Validacion.java
package com.previos.solicitudes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "validacion",
       uniqueConstraints = {
         @UniqueConstraint(name="uk_validacion_token", columnNames = "token"),
         @UniqueConstraint(name="uk_validacion_codigo", columnNames = "codigo")
       })
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Validacion {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 50, nullable = false)
  @NotBlank @Email @Size(max = 50)
  private String email;

  @Column(length = 10, nullable = false)
  @NotBlank @Size(max = 10)
  private String documento;

  // necesitamos precisión de minutos para el vencimiento (15 min)
  @Column(name="fecha", nullable = false)
  private LocalDateTime fecha;

  @Column(length = 20, nullable = false)
  private String estado; // "pendiente" | "validada"

  @Column(length = 100, nullable = false, unique = true)
  private String token;

  @Column(name="codigo", length = 10, nullable = false, unique = true)
  private String codigo;
}

