// service/SolicitudService.java
package com.previos.solicitudes.service;

import com.previos.solicitudes.dto.*;
import com.previos.solicitudes.entity.*;
import com.previos.solicitudes.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SolicitudService {

  private final SolicitudRepository solicitudRepo;
  private final PersonaRepository personaRepo;
  private final EstadoRepository estadoRepo;
  private final UtilService util;

  public List<SolicitudListDTO> listar() {
    List<SolicitudListDTO> out = new ArrayList<>();
    for (Solicitud s : solicitudRepo.findAll()) {
      SolicitudListDTO dto = new SolicitudListDTO();
      dto.setId(s.getId());
      dto.setFecha(String.valueOf(s.getFecha()));
      dto.setSolicitante(s.getSolicitante().getNombre());
      dto.setCodeudor(s.getCodeudor().getNombre());
      dto.setEstado(s.getEstado().getDescripcion());
      dto.setCodigo_radicado(s.getCodigoRadicado());
      out.add(dto);
    }
    return out;
  }

  @Transactional
  public Object registrarSolicitud(SolicitudRegistroRequest req) {
    // personas distintas
    if (req.getSolicitante().getDocumento().equals(req.getCodeudor().getDocumento()))
      return error("Solicitante y codeudor deben ser distintos");

    // no compartir correo/telefono
    if (req.getSolicitante().getEmail().equalsIgnoreCase(req.getCodeudor().getEmail()))
      return error("Solicitante y codeudor no pueden tener el mismo email");
    if (req.getSolicitante().getTelefono().equals(req.getCodeudor().getTelefono()))
      return error("Solicitante y codeudor no pueden tener el mismo teléfono");

    // cargar/crear personas
    Persona solicitante = upsertPersona(req.getSolicitante());
    Persona codeudor    = upsertPersona(req.getCodeudor());

    // bloqueos por estado
    var bloqueantes = List.of("aprobado","solicitud");
    List<Solicitud> existentes = solicitudRepo.findBySolicitanteAndEstado_DescripcionIn(solicitante, bloqueantes);
    if (!existentes.isEmpty())
      return error("El solicitante tiene solicitudes en estado Aprobado o Solicitud");

    // estado destino (rechazada si tiene alguna rechazada, sino 'solicitud')
    String estadoDestino = "solicitud";
    var rechazadas = solicitudRepo.findBySolicitanteAndEstado_DescripcionIn(solicitante, List.of("rechazada"));
    if (!rechazadas.isEmpty()) estadoDestino = "rechazada";

    Estado estado = estadoRepo.findByDescripcion(estadoDestino)
      .orElseThrow(() -> new RuntimeException("Debe precargar estados: solicitud, aprobado, rechazada, finalizada"));

    // generar código único de 10 chars
    String codigo;
    do { codigo = util.randomCode(10); } while (solicitudRepo.existsByCodigoRadicado(codigo));

    Solicitud s = new Solicitud();
    s.setFecha(LocalDate.now());
    s.setSolicitante(solicitante);
    s.setCodeudor(codeudor);
    s.setValor((BigDecimal) null); // opcional
    s.setEstado(estado);
    s.setObservacion(req.getObservacion());
    s.setCodigoRadicado(codigo);

    s = solicitudRepo.save(s);
    return new SolicitudRegistroResponse(s.getId(), String.valueOf(s.getFecha()), s.getCodigoRadicado(), estadoDestino);
  }

  private Persona upsertPersona(SolicitudRegistroRequest.PersonaReq p) {
    var opt = personaRepo.findByDocumento(p.getDocumento());
    Persona persona = opt.orElseGet(Persona::new);
    persona.setDocumento(p.getDocumento());
    persona.setNombre(p.getNombre());
    persona.setEmail(p.getEmail());
    persona.setTelefono(p.getTelefono());
    try {
      persona.setFechaNacimiento(LocalDate.parse(p.getFecha_nacimiento())); // yyyy-MM-dd
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Fecha de nacimiento inválida (use yyyy-MM-dd)");
    }
    return personaRepo.save(persona);
  }

  private Map<String,Object> error(String msg) { return Map.of("error", true, "msg", msg); }
}
