package com.enviodecorreo.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enviodecorreo.email.Entity.Email;

@Repository
public interface EmailRepository extends JpaRepository <Email, Integer> {
    
}
//LO DE ARRIBA ES EL CODIGO ANTERIOR QUE FUE ELIMINAD

