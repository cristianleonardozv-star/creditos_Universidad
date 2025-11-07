// controller/SolicitudController.java
package com.previos.solicitudes.controller;

import com.previos.solicitudes.dto.SolicitudRegistroRequest;
import com.previos.solicitudes.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SolicitudController {

  private final SolicitudService solicitudService;

  // (2) Listar solicitudes
  @GetMapping
  public ResponseEntity<?> listar() {
    return ResponseEntity.ok(solicitudService.listar());
  }

  // (4) Registrar solicitud
  @PostMapping
  public ResponseEntity<?> registrar(@Valid @RequestBody SolicitudRegistroRequest req) {
    Object r = solicitudService.registrarSolicitud(req);
    return ResponseEntity.ok(r);
  }
}
