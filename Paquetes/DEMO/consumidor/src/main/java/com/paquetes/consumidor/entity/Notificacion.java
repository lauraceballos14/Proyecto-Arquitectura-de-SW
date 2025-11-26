package com.paquetes.consumidor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "compra_id")
    private Long compraId;

    private String estado;

    @Column(name = "cliente_cedula")
    private String clienteCedula;

    @Column(name = "cliente_nombre")
    private String clienteNombre;

    private String correo;

    private String celular;

    private BigDecimal total;

    @Lob
    @Column(name = "paquetes", columnDefinition = "LONGTEXT")
    private String paquetes;

    @Column(name = "recibido_en")
    private Instant recibidoEn;
}
