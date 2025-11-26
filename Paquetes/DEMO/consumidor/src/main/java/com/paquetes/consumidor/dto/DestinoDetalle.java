package com.paquetes.consumidor.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DestinoDetalle {
    private Long id;
    private String nombre;
    private String ciudad;
    private String region;
    private String descripcion;
    private BigDecimal precio;
}
