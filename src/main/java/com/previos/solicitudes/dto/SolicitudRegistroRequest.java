// dto/SolicitudRegistroRequest.java
package com.previos.solicitudes.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SolicitudRegistroRequest {
  @NotNull
  private PersonaReq solicitante;

  @NotNull
  private PersonaReq codeudor;

  private String observacion;

  @Data
  public static class PersonaReq {
    @NotBlank @Size(max=10) private String documento;
    @NotBlank @Size(max=100) private String nombre;
    @NotBlank @Email @Size(max=50) private String email;
    @NotBlank @Size(max=10) private String telefono;
    @NotBlank private String fecha_nacimiento; // ISO: yyyy-MM-dd
  }
}
