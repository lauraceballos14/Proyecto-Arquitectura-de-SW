package com.pago.consumidor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pago.consumidor.listener.EstadoPaqueteListener;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoPaqueteListener listener;

    @GetMapping("/ultimo")
    public ResponseEntity<Map<String, Object>> ultimo() {
        return ResponseEntity.ok(listener.getUltimoEstado());
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Map<String, Object>>> historial() {
        return ResponseEntity.ok(listener.getHistorial());
    }
}
