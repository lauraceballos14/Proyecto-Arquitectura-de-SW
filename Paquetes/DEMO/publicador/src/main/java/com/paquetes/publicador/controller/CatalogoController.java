package com.paquetes.publicador.controller;

import com.paquetes.publicador.entity.Destino;
import com.paquetes.publicador.entity.PaqueteTuristico;
import com.paquetes.publicador.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CatalogoController {

    private final CompraService compraService;

    @GetMapping("/destinos")
    public ResponseEntity<List<Destino>> destinos() {
        return ResponseEntity.ok(compraService.listarDestinos());
    }

    @GetMapping("/paquetes")
    public ResponseEntity<List<PaqueteTuristico>> paquetes() {
        return ResponseEntity.ok(compraService.listarPaquetes());
    }
}
