// dto/SolicitudRegistroResponse.java
package com.previos.solicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolicitudRegistroResponse {
  private Integer id;
  private String fecha;
  private String codigo_radicado;
  private String estado;
}
