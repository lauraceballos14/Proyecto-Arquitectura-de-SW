package com.paquetes.consumidor.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paquetes.consumidor.config.RabbitConfig;
import com.paquetes.consumidor.dto.PagoEvent;
import com.paquetes.consumidor.dto.PaqueteDetalle;
import com.paquetes.consumidor.entity.Notificacion;
import com.paquetes.consumidor.entity.PaqueteComprado;
import com.paquetes.consumidor.repository.NotificacionRepository;
import com.paquetes.consumidor.repository.PaqueteCompradoRepository;
import com.paquetes.consumidor.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PagoListener {

    private final ObjectMapper objectMapper;
    private final NotificacionRepository repository;
    private final PaqueteCompradoRepository paqueteCompradoRepository;
    private final EmailService emailService;

    private final AtomicReference<Notificacion> ultimaNotificacion =
            new AtomicReference<>(null);
    private final List<Notificacion> historial =
            Collections.synchronizedList(new ArrayList<>());

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void recibir(String mensaje) {
        try {
            PagoEvent evento = objectMapper.readValue(mensaje, PagoEvent.class);

            Notificacion notificacion = Notificacion.builder()
                    .compraId(evento.getCompraId())
                    .estado(evento.getEstado())
                    .clienteCedula(evento.getClienteCedula())
                    .clienteNombre(evento.getClienteNombre())
                    .correo(evento.getCorreo())
                    .celular(evento.getCelular())
                    .total(evento.getTotal())
                    .paquetes(objectMapper.writeValueAsString(evento.getPaquetes()))
                    .recibidoEn(evento.getFecha() != null ? evento.getFecha() : Instant.now())
                    .build();

            repository.save(notificacion);
            ultimaNotificacion.set(notificacion);
            historial.add(notificacion);
            guardarPaquetes(evento, notificacion);
            emailService.enviarNotificacion(notificacion);

            if ("APROBADO".equalsIgnoreCase(notificacion.getEstado())) {
                System.out.printf("[SMS] Compra #%s aprobada para %s (%s). Total: %s%n",
                        notificacion.getCompraId(), notificacion.getClienteNombre(),
                        notificacion.getCelular(), notificacion.getTotal());
            } else if ("RECHAZADO".equalsIgnoreCase(notificacion.getEstado())) {
                System.out.printf("[SMS] Compra #%s rechazada. Revisa metodo de pago o fondos.%n",
                        notificacion.getCompraId());
            } else {
                System.out.printf("[INFO] Estado %s recibido para compra #%s%n",
                        notificacion.getEstado(), notificacion.getCompraId());
            }

        } catch (Exception e) {
            System.err.println("[Error] No se pudo procesar el mensaje: " + e.getMessage());
        }
    }

    private void guardarPaquetes(PagoEvent evento, Notificacion notificacion) {
        if (evento.getPaquetes() == null || evento.getPaquetes().isEmpty()) {
            return;
        }
        evento.getPaquetes().forEach(p -> {
            try {
                String destinosJson = objectMapper.writeValueAsString(p.getDestinos());
                PaqueteComprado pc = PaqueteComprado.builder()
                        .notificacion(notificacion)
                        .paqueteId(p.getId())
                        .paqueteNombre(p.getNombre())
                        .precioTotal(p.getPrecioTotal())
                        .destinos(destinosJson)
                        .build();
                paqueteCompradoRepository.save(pc);
            } catch (Exception e) {
                System.err.println("[Error] No se pudo guardar paquete comprado: " + e.getMessage());
            }
        });
    }

    public Notificacion getUltimaNotificacion() {
        return ultimaNotificacion.get();
    }

    public List<Notificacion> getHistorial() {
        return historial;
    }
}
