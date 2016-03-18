package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ApuestaRealizadas;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ApuestaRealizadas entity.
 */
public interface ApuestaRealizadasRepository extends JpaRepository<ApuestaRealizadas,Long> {

    @Query("select apuestaRealizadas from ApuestaRealizadas apuestaRealizadas where apuestaRealizadas.aApostador.login = ?#{principal.username}")
    List<ApuestaRealizadas> findByAApostadorIsCurrentUser();

}
