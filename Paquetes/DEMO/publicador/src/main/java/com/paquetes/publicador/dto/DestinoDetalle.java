package com.paquetes.publicador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinoDetalle {
    private Long id;
    private String nombre;
    private String ciudad;
    private String region;
    private String descripcion;
    private BigDecimal precio;
}
