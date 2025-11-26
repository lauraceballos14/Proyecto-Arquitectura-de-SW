package com.paquetes.consumidor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "paquetes_comprados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteComprado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacion_id")
    private Notificacion notificacion;

    @Column(name = "paquete_id")
    private Long paqueteId;

    @Column(name = "paquete_nombre")
    private String paqueteNombre;

    @Column(name = "precio_total")
    private BigDecimal precioTotal;

    @Lob
    @Column(name = "destinos_json", columnDefinition = "LONGTEXT")
    private String destinos;
}
