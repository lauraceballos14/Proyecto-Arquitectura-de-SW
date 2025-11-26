package com.paquetes.publicador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CompraRequest {

    @NotBlank
    private String clienteCedula;

    @NotBlank
    private String clienteNombre;

    @Email
    @NotBlank
    private String correo;

    @NotBlank
    @Size(min = 7, max = 15)
    private String celular;

    @NotEmpty
    private List<Long> paquetesIds;
}
