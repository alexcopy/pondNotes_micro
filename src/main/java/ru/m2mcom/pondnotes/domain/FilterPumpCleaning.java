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

    @Column(name = "description")
    private String description;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public FilterPumpCleaning timestamp(Long timestamp) {
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

    public FilterPumpCleaning userId(Long userId) {
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
            ", description='" + description + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
