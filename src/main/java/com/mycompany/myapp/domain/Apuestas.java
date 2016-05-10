package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Apuestas.
 */
@Entity
@Table(name = "apuestas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Apuestas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "liga_name")
    private String ligaName;

    @Column(name = "partido_start_date")
    private LocalDate partidoStartDate;

    @NotNull
    @Column(name = "partido_time", nullable = false)
    private LocalDate partidoTime;

    @Column(name = "apuesta_name")
    private String apuestaName;

    @Column(name = "a_apostar_odd")
    private Double aApostarOdd;

    @Column(name = "a_apostar_name")
    private String aApostarName;

    @Column(name = "tipo_deporte")
    private String tipoDeporte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLigaName() {
        return ligaName;
    }

    public void setLigaName(String ligaName) {
        this.ligaName = ligaName;
    }

    public LocalDate getPartidoStartDate() {
        return partidoStartDate;
    }

    public void setPartidoStartDate(LocalDate partidoStartDate) {
        this.partidoStartDate = partidoStartDate;
    }

    public LocalDate getPartidoTime() {
        return partidoTime;
    }

    public void setPartidoTime(LocalDate partidoTime) {
        this.partidoTime = partidoTime;
    }

    public String getApuestaName() {
        return apuestaName;
    }

    public void setApuestaName(String apuestaName) {
        this.apuestaName = apuestaName;
    }

    public Double getaApostarOdd() {
        return aApostarOdd;
    }

    public void setaApostarOdd(Double aApostarOdd) {
        this.aApostarOdd = aApostarOdd;
    }

    public String getaApostarName() {
        return aApostarName;
    }

    public void setaApostarName(String aApostarName) {
        this.aApostarName = aApostarName;
    }

    public String getTipoDeporte() {
        return tipoDeporte;
    }

    public void setTipoDeporte(String tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Apuestas apuestas = (Apuestas) o;
        if(apuestas.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, apuestas.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Apuestas{" +
            "id=" + id +
            ", ligaName='" + ligaName + "'" +
            ", partidoStartDate='" + partidoStartDate + "'" +
            ", partidoTime='" + partidoTime + "'" +
            ", apuestaName='" + apuestaName + "'" +
            ", aApostarOdd='" + aApostarOdd + "'" +
            ", aApostarName='" + aApostarName + "'" +
            ", tipoDeporte='" + tipoDeporte + "'" +
            '}';
    }
}
