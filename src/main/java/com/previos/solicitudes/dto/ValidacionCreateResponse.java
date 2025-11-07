// dto/ValidacionCreateResponse.java
package com.previos.solicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidacionCreateResponse {
  private String token;
  private String codigo;
  private String estado;
}
