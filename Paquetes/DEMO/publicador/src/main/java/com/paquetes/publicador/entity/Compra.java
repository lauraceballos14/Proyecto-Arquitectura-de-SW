package com.paquetes.publicador.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "compras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_cedula")
    private String clienteCedula;

    @Column(name = "cliente_nombre")
    private String clienteNombre;

    private String correo;

    private String celular;

    @Enumerated(EnumType.STRING)
    private EstadoCompra estado;

    @Column(name = "total_compra")
    private BigDecimal total;

    @ManyToMany
    @JoinTable(
            name = "compra_paquetes",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "paquete_id")
    )
    private List<PaqueteTuristico> paquetes;

    @Column(name = "creado_en")
    private Instant creadoEn;

    @PrePersist
    public void onCreate() {
        this.creadoEn = Instant.now();
    }
}
