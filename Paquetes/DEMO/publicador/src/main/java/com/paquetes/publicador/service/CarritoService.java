package com.paquetes.publicador.service;

import com.paquetes.publicador.dto.CarritoCheckoutRequest;
import com.paquetes.publicador.dto.CarritoResponse;
import com.paquetes.publicador.dto.PaqueteDetalle;
import com.paquetes.publicador.entity.PaqueteTuristico;
import com.paquetes.publicador.repository.PaqueteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final PaqueteRepository paqueteRepository;
    private final CompraService compraService;

    // Carritos en memoria por correo
    private final Map<String, List<Long>> carritos = new ConcurrentHashMap<>();

    public void agregar(String correo, Long paqueteId) {
        carritos.computeIfAbsent(correo, k -> new ArrayList<>()).add(paqueteId);
    }

    public CarritoResponse ver(String correo) {
        List<Long> ids = carritos.getOrDefault(correo, List.of());
        List<PaqueteTuristico> paquetes = ids.isEmpty() ? List.of() : paqueteRepository.findAllById(ids);
        BigDecimal total = paquetes.stream()
                .map(PaqueteTuristico::getPrecioTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CarritoResponse.builder()
                .correo(correo)
                .paquetes(paquetes.stream()
                        .map(compraService::mapearPaquete)
                        .collect(Collectors.toList()))
                .total(total)
                .build();
    }

    public void limpiar(String correo) {
        carritos.remove(correo);
    }

    public CarritoResponse checkout(String correo, CarritoCheckoutRequest req) {
        List<Long> ids = carritos.getOrDefault(correo, List.of());
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("El carrito esta vacio para " + correo);
        }
        var compraReq = new com.paquetes.publicador.dto.CompraRequest();
        compraReq.setClienteCedula(req.getClienteCedula());
        compraReq.setClienteNombre(req.getClienteNombre());
        compraReq.setCorreo(req.getCorreo());
        compraReq.setCelular(req.getCelular());
        compraReq.setPaquetesIds(ids);

        var compra = compraService.crearCompra(compraReq);
        // Limpiar carrito despues de crear la compra
        limpiar(correo);
        return ver(correo);
    }
}
