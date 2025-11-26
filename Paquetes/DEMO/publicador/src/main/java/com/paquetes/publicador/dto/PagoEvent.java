package com.paquetes.publicador.dto;

import com.paquetes.publicador.entity.EstadoCompra;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class PagoEvent {
    private Long compraId;
    private EstadoCompra estado;
    private String clienteCedula;
    private String clienteNombre;
    private String correo;
    private String celular;
    private BigDecimal total;
    private List<PaqueteDetalle> paquetes;
    private Instant fecha;
}
