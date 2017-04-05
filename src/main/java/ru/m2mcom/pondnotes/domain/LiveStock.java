package ru.m2mcom.pondnotes.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import ru.m2mcom.pondnotes.domain.enumeration.StockCase;

/**
 * A LiveStock.
 */
@Entity
@Table(name = "live_stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "livestock")
public class LiveStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private StockCase reason;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "qty", nullable = false)
    private Integer qty;

    @NotNull
    @Column(name = "temp_val", nullable = false)
    private Double tempVal;

    @Column(name = "timestamp")
    private Integer timestamp;

    @Column(name = "user_id")
    private Integer userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public LiveStock date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public StockCase getReason() {
        return reason;
    }

    public LiveStock reason(StockCase reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(StockCase reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public LiveStock description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQty() {
        return qty;
    }

    public LiveStock qty(Integer qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getTempVal() {
        return tempVal;
    }

    public LiveStock tempVal(Double tempVal) {
        this.tempVal = tempVal;
        return this;
    }

    public void setTempVal(Double tempVal) {
        this.tempVal = tempVal;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public LiveStock timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public LiveStock userId(Integer userId) {
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
        LiveStock liveStock = (LiveStock) o;
        if (liveStock.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, liveStock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LiveStock{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", reason='" + reason + "'" +
            ", description='" + description + "'" +
            ", qty='" + qty + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
