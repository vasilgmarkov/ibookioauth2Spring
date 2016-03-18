package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Resultados.
 */
@Entity
@Table(name = "resultados")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resultados implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "evento")
    private String evento;
    
    @Column(name = "ganador")
    private String ganador;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvento() {
        return evento;
    }
    
    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getGanador() {
        return ganador;
    }
    
    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resultados resultados = (Resultados) o;
        if(resultados.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, resultados.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Resultados{" +
            "id=" + id +
            ", evento='" + evento + "'" +
            ", ganador='" + ganador + "'" +
            '}';
    }
}
