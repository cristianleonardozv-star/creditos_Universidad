package com.previos.solicitudes.service;

import com.previos.solicitudes.dto.*;
import com.previos.solicitudes.entity.Validacion;
import com.previos.solicitudes.repository.ValidacionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ValidacionService {

    private final ValidacionRepository validacionRepo;
    private final UtilService util;

    // ============================================================
    // CREAR VALIDACIÓN (correo/documento)
    // ============================================================
    @Transactional
    public ValidacionCreateResponse crear(ValidacionCreateRequest req) {
        Validacion v = new Validacion();
        v.setEmail(req.getEmail());
        v.setDocumento(req.getDocumento());
        v.setFecha(LocalDateTime.now());
        v.setEstado("pendiente");

        // generar token único
        String token;
        do {
            token = util.randomCode(64);
        } while (validacionRepo.findByToken(token).isPresent());

        // generar código único
        String codigo = generarCodigoUnico();

        v.setToken(token);
        v.setCodigo(codigo);
        validacionRepo.save(v);

        return new ValidacionCreateResponse(token, codigo, v.getEstado());
    }

    // ============================================================
    // CONFIRMAR VALIDACIÓN (por token)
    // ============================================================
    @Transactional
    public Object confirmar(String token) {
        var opt = validacionRepo.findByToken(token);
        if (opt.isEmpty()) return error("Token inválido");

        var v = opt.get();

        // vencido si pasaron más de 15 minutos
        long minutos = Duration.between(v.getFecha(), LocalDateTime.now()).toMinutes();
        if (minutos > 15) return error("Token vencido");

        if ("validada".equalsIgnoreCase(v.getEstado())) return error("Token ya validado");

        v.setEstado("validada");
        validacionRepo.save(v);

        return new ValidacionConfirmResponse(true);
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================
    private java.util.Map<String, Object> error(String msg) {
        return java.util.Map.of("error", true, "msg", msg);
    }

    private String generarCodigoUnico() {
        String codigo;
        boolean existe;
        do {
            codigo = util.randomCode(10);
            existe = validacionRepo.findAll()
                    .stream()
                    .anyMatch(v -> v.getCodigo().equals(codigo));
        } while (existe);
        return codigo;
    }
}
