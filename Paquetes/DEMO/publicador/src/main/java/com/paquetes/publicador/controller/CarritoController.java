package com.paquetes.publicador.controller;

import com.paquetes.publicador.dto.CarritoCheckoutRequest;
import com.paquetes.publicador.dto.CarritoItemRequest;
import com.paquetes.publicador.dto.CarritoResponse;
import com.paquetes.publicador.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @PostMapping("/items")
    public ResponseEntity<CarritoResponse> agregar(@Valid @RequestBody CarritoItemRequest req) {
        carritoService.agregar(req.getCorreo(), req.getPaqueteId());
        return ResponseEntity.ok(carritoService.ver(req.getCorreo()));
    }

    @GetMapping("/{correo:.+}")
    public ResponseEntity<CarritoResponse> ver(@PathVariable String correo) {
        return ResponseEntity.ok(carritoService.ver(correo));
    }

    @DeleteMapping("/{correo:.+}")
    public ResponseEntity<Void> limpiar(@PathVariable String correo) {
        carritoService.limpiar(correo);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{correo:.+}/checkout")
    public ResponseEntity<CarritoResponse> checkout(@PathVariable String correo,
                                                    @Valid @RequestBody CarritoCheckoutRequest req) {
        // Usamos el correo del path para el carrito, y el correo en el body para la compra (pueden ser iguales)
        CarritoResponse respuesta = carritoService.checkout(correo, req);
        return ResponseEntity.ok(respuesta);
    }
}
