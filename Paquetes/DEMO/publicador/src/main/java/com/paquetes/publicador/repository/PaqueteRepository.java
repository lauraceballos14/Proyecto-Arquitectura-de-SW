package com.paquetes.publicador.repository;

import com.paquetes.publicador.entity.PaqueteTuristico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaqueteRepository extends JpaRepository<PaqueteTuristico, Long> {
}
