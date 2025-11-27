package com.paquetes.publicador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarritoCheckoutRequest {

    @NotBlank
    private String clienteCedula;

    @NotBlank
    private String clienteNombre;

    @Email
    @NotBlank
    private String correo;

    @NotBlank
    private String celular;
}
