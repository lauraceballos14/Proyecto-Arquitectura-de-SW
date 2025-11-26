package com.paquetes.consumidor.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PaqueteDetalle {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioTotal;
    private List<DestinoDetalle> destinos;
}
