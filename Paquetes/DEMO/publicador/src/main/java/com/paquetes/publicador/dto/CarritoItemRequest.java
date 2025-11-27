package com.paquetes.publicador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarritoItemRequest {

    @Email
    private String correo;

    @NotNull
    private Long paqueteId;
}
