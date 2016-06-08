package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ApuestaRealizadas;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Spring Data JPA repository for the ApuestaRealizadas entity.
 */
public interface ApuestaRealizadasRepository extends JpaRepository<ApuestaRealizadas,Long> {

    @Query("select apuestaRealizadas from ApuestaRealizadas apuestaRealizadas where apuestaRealizadas.aApostador.login = ?#{principal.username}")
    List<ApuestaRealizadas> findByAApostadorIsCurrentUser();

    @Query("SELECT a FROM ApuestaRealizadas a GROUP BY eventoApostado order by COUNT(eventoApostado) DESC ")
    Page<ApuestaRealizadas> findByTopApuestaRealizadas(Pageable var1);



}
