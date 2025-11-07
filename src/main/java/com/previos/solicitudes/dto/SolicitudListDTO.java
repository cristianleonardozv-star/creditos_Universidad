// dto/SolicitudListDTO.java
package com.previos.solicitudes.dto;

import lombok.Data;

@Data
public class SolicitudListDTO {
  private Integer id;
  private String fecha;
  private String solicitante;
  private String codeudor;
  private String estado;
  private String codigo_radicado;
}
