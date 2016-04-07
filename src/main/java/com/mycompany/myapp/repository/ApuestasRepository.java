package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Apuestas;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Apuestas entity.
 */
public interface ApuestasRepository extends JpaRepository<Apuestas,Long> {
        List<Apuestas> findByapuestaNameEquals(String name);
        List<Apuestas> findByligaNameEquals(String ligaName);

       @Query("SELECT a FROM Apuestas a WHERE a.apuestaName LIKE '%Match Betting%' and a.ligaName =:ligaName ")
        public List<Apuestas> findByapuestaNameContainingAndligaNameEquals(@Param ("ligaName")String ligaName);
      //  List<Apuestas> findAllByCanastasGreaterThanEqualOrderByCanastasDesc(Integer canastas);


}
