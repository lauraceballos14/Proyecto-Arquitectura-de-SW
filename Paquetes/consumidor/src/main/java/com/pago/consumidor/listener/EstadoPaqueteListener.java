package com.pago.consumidor.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pago.consumidor.config.RabbitConfig;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class EstadoPaqueteListener {

    private final AtomicReference<Map<String, Object>> ultimoEstado = new AtomicReference<>(Map.of("estado", "SIN_DATOS"));
    private final List<Map<String, Object>> historial = Collections.synchronizedList(new ArrayList<>());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void recibir(String mensaje) {
        try {
            Map<String, Object> payload = objectMapper.readValue(mensaje, Map.class);
            payload.putIfAbsent("fecha", Instant.now().toString());
            ultimoEstado.set(payload);
            historial.add(payload);
        } catch (Exception e) {
            Map<String, Object> fallback = Map.of("estado", mensaje, "fecha", Instant.now().toString());
            ultimoEstado.set(fallback);
            historial.add(fallback);
        }
    }

    public Map<String, Object> getUltimoEstado() {
        return ultimoEstado.get();
    }

    public List<Map<String, Object>> getHistorial() {
        return historial;
    }
}
