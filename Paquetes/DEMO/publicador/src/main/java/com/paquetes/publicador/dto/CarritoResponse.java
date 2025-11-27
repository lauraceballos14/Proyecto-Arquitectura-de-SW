package com.paquetes.publicador.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CarritoResponse {
    private String correo;
    private List<PaqueteDetalle> paquetes;
    private BigDecimal total;
}
