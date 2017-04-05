package ru.m2mcom.pondnotes.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FilterPumpCleaning entity.
 */
public class FilterPumpCleaningDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime cleaningDate;

    @NotNull
    private Double tempVal;

    private Integer timestamp;

    private String description;

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

    public void setCleaningDate(ZonedDateTime cleaningDate) {
        this.cleaningDate = cleaningDate;
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        FilterPumpCleaningDTO filterPumpCleaningDTO = (FilterPumpCleaningDTO) o;

        if ( ! Objects.equals(id, filterPumpCleaningDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FilterPumpCleaningDTO{" +
            "id=" + id +
            ", cleaningDate='" + cleaningDate + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", description='" + description + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
