package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Resultados;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Resultados entity.
 */
public interface ResultadosRepository extends JpaRepository<Resultados,Long> {

}
