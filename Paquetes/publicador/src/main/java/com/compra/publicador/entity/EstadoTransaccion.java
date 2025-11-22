package com.compra.publicador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoTransaccion {
    private String cliente;
    private String correo;
    private String estado;
}
