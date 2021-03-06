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

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "reading", nullable = false)
    private Double reading;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public MeterReading timestamp(Long timestamp) {
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

    public MeterReading userId(Long userId) {
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
            ", description='" + description + "'" +
            ", reading='" + reading + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
