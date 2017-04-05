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
 * A MeterReading.
 */
@Entity
@Table(name = "meter_reading")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "meterreading")
public class MeterReading implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "reading_date", nullable = false)
    private ZonedDateTime readingDate;

    @NotNull
    @Column(name = "reading", nullable = false)
    private Double reading;

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

    public ZonedDateTime getReadingDate() {
        return readingDate;
    }

    public MeterReading readingDate(ZonedDateTime readingDate) {
        this.readingDate = readingDate;
        return this;
    }

    public void setReadingDate(ZonedDateTime readingDate) {
        this.readingDate = readingDate;
    }

    public Double getReading() {
        return reading;
    }

    public MeterReading reading(Double reading) {
        this.reading = reading;
        return this;
    }

    public void setReading(Double reading) {
        this.reading = reading;
    }

    public Double getTempVal() {
        return tempVal;
    }

    public MeterReading tempVal(Double tempVal) {
        this.tempVal = tempVal;
        return this;
    }

    public void setTempVal(Double tempVal) {
        this.tempVal = tempVal;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public MeterReading timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public MeterReading description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public MeterReading userId(Integer userId) {
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
        MeterReading meterReading = (MeterReading) o;
        if (meterReading.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, meterReading.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MeterReading{" +
            "id=" + id +
            ", readingDate='" + readingDate + "'" +
            ", reading='" + reading + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", description='" + description + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
