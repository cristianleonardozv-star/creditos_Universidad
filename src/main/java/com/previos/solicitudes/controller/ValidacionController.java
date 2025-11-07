// controller/ValidacionController.java
package com.previos.solicitudes.controller;

import com.previos.solicitudes.dto.ValidacionCreateRequest;
import com.previos.solicitudes.service.ValidacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ValidacionController {

  private final ValidacionService validacionService;

  // (3) Crear validación de email (pendiente)
  @PostMapping
  public ResponseEntity<?> crear(@Valid @RequestBody ValidacionCreateRequest req) {
    return ResponseEntity.ok(validacionService.crear(req));
  }

  // (5) Confirmar validación por token (15 minutos de vigencia)
  @PostMapping("/confirmar")
  public ResponseEntity<?> confirmar(@RequestParam String token) {
    return ResponseEntity.ok(validacionService.confirmar(token));
  }
}
