package ru.m2mcom.pondnotes.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ChemicalAnalysis.
 */
@Entity
@Table(name = "chemical_analysis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "chemicalanalysis")
public class ChemicalAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Column(name = "n_o_2")
    private String nO2;

    @Column(name = "n_o_3")
    private String nO3;

    @Column(name = "n_h_4")
    private String nH4;

    @Column(name = "ph")
    private String ph;

    @NotNull
    @Column(name = "temp_val", nullable = false)
    private Double tempVal;

    @Column(name = "timestamp")
    private Integer timestamp;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chemical_analysis_tank",
               joinColumns = @JoinColumn(name="chemical_analyses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tanks_id", referencedColumnName="id"))
    private Set<Tank> tanks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public ChemicalAnalysis date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getnO2() {
        return nO2;
    }

    public ChemicalAnalysis nO2(String nO2) {
        this.nO2 = nO2;
        return this;
    }

    public void setnO2(String nO2) {
        this.nO2 = nO2;
    }

    public String getnO3() {
        return nO3;
    }

    public ChemicalAnalysis nO3(String nO3) {
        this.nO3 = nO3;
        return this;
    }

    public void setnO3(String nO3) {
        this.nO3 = nO3;
    }

    public String getnH4() {
        return nH4;
    }

    public ChemicalAnalysis nH4(String nH4) {
        this.nH4 = nH4;
        return this;
    }

    public void setnH4(String nH4) {
        this.nH4 = nH4;
    }

    public String getPh() {
        return ph;
    }

    public ChemicalAnalysis ph(String ph) {
        this.ph = ph;
        return this;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public Double getTempVal() {
        return tempVal;
    }

    public ChemicalAnalysis tempVal(Double tempVal) {
        this.tempVal = tempVal;
        return this;
    }

    public void setTempVal(Double tempVal) {
        this.tempVal = tempVal;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public ChemicalAnalysis timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Tank> getTanks() {
        return tanks;
    }

    public ChemicalAnalysis tanks(Set<Tank> tanks) {
        this.tanks = tanks;
        return this;
    }

    public ChemicalAnalysis addTank(Tank tank) {
        this.tanks.add(tank);
        return this;
    }

    public ChemicalAnalysis removeTank(Tank tank) {
        this.tanks.remove(tank);
        return this;
    }

    public void setTanks(Set<Tank> tanks) {
        this.tanks = tanks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChemicalAnalysis chemicalAnalysis = (ChemicalAnalysis) o;
        if (chemicalAnalysis.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chemicalAnalysis.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChemicalAnalysis{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", nO2='" + nO2 + "'" +
            ", nO3='" + nO3 + "'" +
            ", nH4='" + nH4 + "'" +
            ", ph='" + ph + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
