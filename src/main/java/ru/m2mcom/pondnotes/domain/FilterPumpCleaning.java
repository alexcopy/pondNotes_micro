package ru.m2mcom.pondnotes.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A FilterPumpCleaning.
 */
@Entity
@Table(name = "filter_pump_cleaning")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "filterpumpcleaning")
public class FilterPumpCleaning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cleaning_date", nullable = false)
    private ZonedDateTime cleaningDate;

    @NotNull
    @Column(name = "temp_val", nullable = false)
    private Double tempVal;

    @Column(name = "timestamp")
    private Integer timestamp;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Integer userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCleaningDate() {
        return cleaningDate;
    }

    public FilterPumpCleaning cleaningDate(ZonedDateTime cleaningDate) {
        this.cleaningDate = cleaningDate;
        return this;
    }

    public void setCleaningDate(ZonedDateTime cleaningDate) {
        this.cleaningDate = cleaningDate;
    }

    public Double getTempVal() {
        return tempVal;
    }

    public FilterPumpCleaning tempVal(Double tempVal) {
        this.tempVal = tempVal;
        return this;
    }

    public void setTempVal(Double tempVal) {
        this.tempVal = tempVal;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public FilterPumpCleaning timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public FilterPumpCleaning description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public FilterPumpCleaning userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
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
        FilterPumpCleaning filterPumpCleaning = (FilterPumpCleaning) o;
        if (filterPumpCleaning.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, filterPumpCleaning.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FilterPumpCleaning{" +
            "id=" + id +
            ", cleaningDate='" + cleaningDate + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", description='" + description + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
