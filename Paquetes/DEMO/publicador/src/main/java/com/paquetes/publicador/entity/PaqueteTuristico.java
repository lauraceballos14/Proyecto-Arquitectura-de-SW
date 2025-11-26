package com.paquetes.publicador.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "paquetes_turisticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteTuristico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @ManyToMany
    @JoinTable(
            name = "paquete_destinos",
            joinColumns = @JoinColumn(name = "paquete_id"),
            inverseJoinColumns = @JoinColumn(name = "destino_id")
    )
    private List<Destino> destinos;

    @Column(name = "precio_total")
    private BigDecimal precioTotal;
}
