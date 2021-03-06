package ru.m2mcom.pondnotes.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
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
    private Long timestamp;

    @Column(name = "user_id")
    private Long userId;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public ChemicalAnalysis timestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Long timestamp) {
        if (timestamp != null) {
            this.timestamp = timestamp;

        } else {
            this.timestamp = Instant.now().getEpochSecond();
        }
    }

    public Long getUserId() {
        return userId;
    }

    public ChemicalAnalysis userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
            ", userId='" + userId + "'" +
            '}';
    }
}
