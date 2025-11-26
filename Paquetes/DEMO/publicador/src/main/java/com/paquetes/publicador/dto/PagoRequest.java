package com.paquetes.publicador.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PagoRequest {
    @NotBlank
    private String estado;
}
