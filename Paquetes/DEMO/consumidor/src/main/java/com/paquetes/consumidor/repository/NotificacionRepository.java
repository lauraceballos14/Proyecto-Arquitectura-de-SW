package com.paquetes.consumidor.repository;

import com.paquetes.consumidor.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    Notificacion findTopByOrderByIdDesc();
}
