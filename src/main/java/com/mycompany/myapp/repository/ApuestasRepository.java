package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Apuestas;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Apuestas entity.
 */
public interface ApuestasRepository extends JpaRepository<Apuestas,Long> {
        List<Apuestas> findByapuestaNameEquals(String name);
        List<Apuestas> findByligaNameEquals(String ligaName);
      //  List<Apuestas> findAllByCanastasGreaterThanEqualOrderByCanastasDesc(Integer canastas);


}
