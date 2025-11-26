package com.paquetes.publicador.controller;

import com.paquetes.publicador.dto.CompraRequest;
import com.paquetes.publicador.dto.PagoRequest;
import com.paquetes.publicador.entity.Compra;
import com.paquetes.publicador.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<Compra> crearCompra(@Valid @RequestBody CompraRequest request) {
        return ResponseEntity.ok(compraService.crearCompra(request));
    }

    @PostMapping("/{id}/pagos")
    public ResponseEntity<Compra> actualizarEstadoPago(@PathVariable Long id,
                                                       @Valid @RequestBody PagoRequest request) {
        return ResponseEntity.ok(compraService.actualizarEstado(id, request.getEstado()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.obtenerCompra(id));
    }
}
