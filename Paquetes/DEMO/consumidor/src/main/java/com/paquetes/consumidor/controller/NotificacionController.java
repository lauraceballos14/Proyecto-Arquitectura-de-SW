package com.paquetes.consumidor.controller;

import com.paquetes.consumidor.entity.Notificacion;
import com.paquetes.consumidor.entity.PaqueteComprado;
import com.paquetes.consumidor.repository.NotificacionRepository;
import com.paquetes.consumidor.repository.PaqueteCompradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionRepository notificacionRepository;
    private final PaqueteCompradoRepository paqueteCompradoRepository;

    @GetMapping
    public List<Notificacion> getTodas() {
        return notificacionRepository.findAll();
    }

    @GetMapping("/ultima")
    public Notificacion getUltima() {
        return notificacionRepository.findTopByOrderByIdDesc();
    }

    @GetMapping("/paquetes")
    public List<PaqueteComprado> getPaquetesComprados() {
        return paqueteCompradoRepository.findAll();
    }
    @GetMapping("/historial")
    public List<Notificacion> getHistorial() {
        return notificacionRepository.findAll();
       }
}
