package com.paquetes.consumidor.repository;

import com.paquetes.consumidor.entity.PaqueteComprado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaqueteCompradoRepository extends JpaRepository<PaqueteComprado, Long> {
}
