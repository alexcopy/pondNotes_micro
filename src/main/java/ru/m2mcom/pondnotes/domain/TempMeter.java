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
 * A TempMeter.
 */
@Entity
@Table(name = "temp_meter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tempmeter")
public class TempMeter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reading_date")
    private ZonedDateTime readingDate;

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

    public TempMeter readingDate(ZonedDateTime readingDate) {
        this.readingDate = readingDate;
        return this;
    }

    public void setReadingDate(ZonedDateTime readingDate) {
        this.readingDate = readingDate;
    }

    public Double getTempVal() {
        return tempVal;
    }

    public TempMeter tempVal(Double tempVal) {
        this.tempVal = tempVal;
        return this;
    }

    public void setTempVal(Double tempVal) {
        this.tempVal = tempVal;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public TempMeter timestamp(Long timestamp) {
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

    public TempMeter userId(Long userId) {
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
        TempMeter tempMeter = (TempMeter) o;
        if (tempMeter.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tempMeter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TempMeter{" +
            "id=" + id +
            ", readingDate='" + readingDate + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
