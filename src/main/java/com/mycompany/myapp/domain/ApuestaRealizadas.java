package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ApuestaRealizadas.
 */
@Entity
@Table(name = "apuesta_realizadas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApuestaRealizadas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad_apostada", nullable = false)
    private Double cantidadApostada;

    @NotNull
    @Min(value = 1)
    @Column(name = "cuota", nullable = false)
    private Double cuota;

    @NotNull
    @Column(name = "evento_apostado", nullable = false)
    private String eventoApostado;

    @NotNull
    @Column(name = "ganador_apuesta", nullable = false)
    private String ganadorApuesta;

    @ManyToOne
    @JoinColumn(name = "a_apostador_id")
    private User aApostador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCantidadApostada() {
        return cantidadApostada;
    }

    public void setCantidadApostada(Double cantidadApostada) {
        this.cantidadApostada = cantidadApostada;
    }

    public Double getCuota() {
        return cuota;
    }

    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }

    public String getEventoApostado() {
        return eventoApostado;
    }

    public void setEventoApostado(String eventoApostado) {
        this.eventoApostado = eventoApostado;
    }

    public String getGanadorApuesta() {
        return ganadorApuesta;
    }

    public void setGanadorApuesta(String ganadorApuesta) {
        this.ganadorApuesta = ganadorApuesta;
    }

    public User getAApostador() {
        return aApostador;
    }

    public void setAApostador(User user) {
        this.aApostador = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApuestaRealizadas apuestaRealizadas = (ApuestaRealizadas) o;
        if(apuestaRealizadas.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, apuestaRealizadas.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApuestaRealizadas{" +
            "id=" + id +
            ", cantidadApostada=" + cantidadApostada +
            ", cuota=" + cuota +
            ", eventoApostado='" + eventoApostado + '\'' +
            ", ganadorApuesta='" + ganadorApuesta + '\'' +
            ", aApostador=" + aApostador +
            '}';
    }
}
