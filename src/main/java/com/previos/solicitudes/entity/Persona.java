// entity/Persona.java
package com.previos.solicitudes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "persona")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Persona {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 10, nullable = false)
  @NotBlank @Size(max = 10)
  private String documento;

  @Column(length = 100, nullable = false)
  @NotBlank @Size(max = 100)
  private String nombre;

  @Column(length = 50, nullable = false)
  @NotBlank @Email @Size(max = 50)
  private String email;

  @Column(name = "telefono", length = 10, nullable = false)
  @NotBlank @Size(max = 10)
  private String telefono;

  @Column(name = "fecha_nacimiento", nullable = false)
  @NotNull
  private LocalDate fechaNacimiento;
}
