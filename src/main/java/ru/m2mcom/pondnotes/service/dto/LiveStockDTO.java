package ru.m2mcom.pondnotes.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.pondnotes.domain.enumeration.StockCase;

/**
 * A DTO for the LiveStock entity.
 */
public class LiveStockDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;

    private StockCase reason;

    private String description;

    @NotNull
    private Integer qty;

    @NotNull
    private Double tempVal;

    private Integer timestamp;

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

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
    public StockCase getReason() {
        return reason;
    }

    public void setReason(StockCase reason) {
        this.reason = reason;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public Double getTempVal() {
        return tempVal;
    }

    public void setTempVal(Double tempVal) {
        this.tempVal = tempVal;
    }
    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
    public Integer getUserId() {
        return userId;
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

        LiveStockDTO liveStockDTO = (LiveStockDTO) o;

        if ( ! Objects.equals(id, liveStockDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LiveStockDTO{" +
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
