package ru.m2mcom.pondnotes.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WaterChange entity.
 */
public class WaterChangeDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime changeDate;

    private String description;

    @NotNull
    private Double readingBefore;

    @NotNull
    private Double readingAfter;

    @NotNull
    private Double tempVal;

    private Integer timestamp;

    private Integer userId;

    private Long tankId;

    private String tankTankName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(ZonedDateTime changeDate) {
        this.changeDate = changeDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Double getReadingBefore() {
        return readingBefore;
    }

    public void setReadingBefore(Double readingBefore) {
        this.readingBefore = readingBefore;
    }
    public Double getReadingAfter() {
        return readingAfter;
    }

    public void setReadingAfter(Double readingAfter) {
        this.readingAfter = readingAfter;
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

    public Long getTankId() {
        return tankId;
    }

    public void setTankId(Long tankId) {
        this.tankId = tankId;
    }

    public String getTankTankName() {
        return tankTankName;
    }

    public void setTankTankName(String tankTankName) {
        this.tankTankName = tankTankName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WaterChangeDTO waterChangeDTO = (WaterChangeDTO) o;

        if ( ! Objects.equals(id, waterChangeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WaterChangeDTO{" +
            "id=" + id +
            ", changeDate='" + changeDate + "'" +
            ", description='" + description + "'" +
            ", readingBefore='" + readingBefore + "'" +
            ", readingAfter='" + readingAfter + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
