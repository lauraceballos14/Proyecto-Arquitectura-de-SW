package com.paquetes.consumidor.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class PagoEvent {
    private Long compraId;
    private String estado;
    private String clienteCedula;
    private String clienteNombre;
    private String correo;
    private String celular;
    private BigDecimal total;
    private List<PaqueteDetalle> paquetes;
    private Instant fecha;
}
