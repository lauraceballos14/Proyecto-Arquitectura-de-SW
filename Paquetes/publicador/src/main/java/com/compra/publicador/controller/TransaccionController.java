package com.compra.publicador.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compra.publicador.config.RabbitConfig;
import com.compra.publicador.entity.EstadoTransaccion;

import java.util.Set;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    private static final Set<String> ESTADOS_VALIDOS = Set.of("EXITOSA", "RECHAZADA", "ANULADA");

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/{estado}")
    public ResponseEntity<String> publicarEstado(@PathVariable String estado) {
        String estadoUpper = estado.toUpperCase();
        if (!ESTADOS_VALIDOS.contains(estadoUpper)) {
            return ResponseEntity.badRequest().body("Estado invalido. Usa EXITOSA, RECHAZADA o ANULADA.");
        }

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, estadoUpper);
        return ResponseEntity.ok("Estado enviado: " + estadoUpper);
    }

    @GetMapping("/demo")
    public ResponseEntity<String> publicarDemo() {
        EstadoTransaccion dto = new EstadoTransaccion(
                "daniela moreno",
                "dani.morleo25@gmail.com",
                "EXITOSA"
        );
        try {
            String payload = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, payload);
            return ResponseEntity.ok("Mensaje demo enviado");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al serializar el mensaje demo: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> publicarEstadoJson(@RequestBody EstadoTransaccion dto) {
        String estadoUpper = dto.getEstado() == null ? "" : dto.getEstado().toUpperCase();
        if (!ESTADOS_VALIDOS.contains(estadoUpper)) {
            return ResponseEntity.badRequest().body("Estado invalido. Usa EXITOSA, RECHAZADA o ANULADA.");
        }
        dto.setEstado(estadoUpper);
        try {
            String payload = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, payload);
            return ResponseEntity.ok("Mensaje enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al serializar el mensaje: " + e.getMessage());
        }
    }
}
