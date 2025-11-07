// entity/Estado.java
package com.previos.solicitudes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estado")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Estado {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "descripcion", length = 20, nullable = false)
  @NotBlank @Size(max = 20)
  private String descripcion; // "solicitud", "aprobado", "rechazada", "finalizada"
}
