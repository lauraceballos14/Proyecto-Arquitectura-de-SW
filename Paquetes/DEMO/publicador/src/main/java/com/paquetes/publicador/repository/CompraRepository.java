package com.paquetes.publicador.repository;

import com.paquetes.publicador.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
