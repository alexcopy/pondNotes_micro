package ru.m2mcom.pondnotes.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.pondnotes.domain.enumeration.TankType;

/**
 * A DTO for the Tank entity.
 */
public class TankDTO implements Serializable {

    private Long id;

    @NotNull
    private String tankName;

    @NotNull
    private TankType tankType;

    private String description;

    private Long timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }
    public TankType getTankType() {
        return tankType;
    }

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TankDTO tankDTO = (TankDTO) o;

        if ( ! Objects.equals(id, tankDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TankDTO{" +
            "id=" + id +
            ", tankName='" + tankName + "'" +
            ", tankType='" + tankType + "'" +
            ", description='" + description + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
