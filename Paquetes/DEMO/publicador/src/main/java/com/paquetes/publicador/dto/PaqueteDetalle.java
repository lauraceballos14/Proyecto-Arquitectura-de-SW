package com.paquetes.publicador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaqueteDetalle {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioTotal;
    private List<DestinoDetalle> destinos;
}
