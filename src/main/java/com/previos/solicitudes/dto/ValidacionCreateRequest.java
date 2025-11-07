// dto/ValidacionCreateRequest.java
package com.previos.solicitudes.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ValidacionCreateRequest {
  @NotBlank @Email @Size(max = 50)
  private String email;

  @NotBlank @Size(max = 10)
  private String documento;
}

