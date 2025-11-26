package com.paquetes.publicador.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paquetes.publicador.config.RabbitConfig;
import com.paquetes.publicador.dto.CompraRequest;
import com.paquetes.publicador.dto.DestinoDetalle;
import com.paquetes.publicador.dto.PagoEvent;
import com.paquetes.publicador.dto.PaqueteDetalle;
import com.paquetes.publicador.entity.Compra;
import com.paquetes.publicador.entity.Destino;
import com.paquetes.publicador.entity.EstadoCompra;
import com.paquetes.publicador.entity.PaqueteTuristico;
import com.paquetes.publicador.repository.CompraRepository;
import com.paquetes.publicador.repository.DestinoRepository;
import com.paquetes.publicador.repository.PaqueteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final PaqueteRepository paqueteRepository;
    private final DestinoRepository destinoRepository;
    private final CompraRepository compraRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public List<Destino> listarDestinos() {
        return destinoRepository.findAll();
    }

    public List<PaqueteTuristico> listarPaquetes() {
        return paqueteRepository.findAll();
    }

    public Compra obtenerCompra(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada: " + id));
    }

    @Transactional
    public Compra crearCompra(CompraRequest request) {
        List<PaqueteTuristico> paquetes = paqueteRepository.findAllById(request.getPaquetesIds());

        if (paquetes.isEmpty() || paquetes.size() != request.getPaquetesIds().size()) {
            throw new IllegalArgumentException("Uno o mas paquetes no existen, revisa los ids enviados");
        }

        BigDecimal total = paquetes.stream()
                .map(PaqueteTuristico::getPrecioTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Compra compra = Compra.builder()
                .clienteCedula(request.getClienteCedula())
                .clienteNombre(request.getClienteNombre())
                .correo(request.getCorreo())
                .celular(request.getCelular())
                .estado(EstadoCompra.EN_PROCESO)
                .total(total)
                .paquetes(paquetes)
                .build();

        Compra guardada = compraRepository.save(compra);
        enviarEvento(guardada);
        return guardada;
    }

    @Transactional
    public Compra actualizarEstado(Long id, String nuevoEstado) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada: " + id));

        EstadoCompra estadoCompra = parseEstado(nuevoEstado);
        compra.setEstado(estadoCompra);
        Compra actualizada = compraRepository.save(compra);
        enviarEvento(actualizada);
        return actualizada;
    }

    private EstadoCompra parseEstado(String estado) {
        try {
            return EstadoCompra.valueOf(estado.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new IllegalArgumentException("Estado invalido. Usa APROBADO, RECHAZADO o EN_PROCESO");
        }
    }

    private void enviarEvento(Compra compra) {
        try {
            PagoEvent event = PagoEvent.builder()
                    .compraId(compra.getId())
                    .estado(compra.getEstado())
                    .clienteCedula(compra.getClienteCedula())
                    .clienteNombre(compra.getClienteNombre())
                    .correo(compra.getCorreo())
                    .celular(compra.getCelular())
                    .total(compra.getTotal())
                    .paquetes(compra.getPaquetes()
                            .stream()
                            .map(this::mapearPaquete)
                            .collect(Collectors.toList()))
                    .fecha(Instant.now())
                    .build();

            String payload = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, payload);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo publicar el evento en RabbitMQ", e);
        }
    }

    private PaqueteDetalle mapearPaquete(PaqueteTuristico paquete) {
        return PaqueteDetalle.builder()
                .id(paquete.getId())
                .nombre(paquete.getNombre())
                .descripcion(paquete.getDescripcion())
                .precioTotal(paquete.getPrecioTotal())
                .destinos(paquete.getDestinos().stream()
                        .map(d -> DestinoDetalle.builder()
                                .id(d.getId())
                                .nombre(d.getNombre())
                                .ciudad(d.getCiudad())
                                .region(d.getRegion())
                                .descripcion(d.getDescripcion())
                                .precio(d.getPrecio())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
