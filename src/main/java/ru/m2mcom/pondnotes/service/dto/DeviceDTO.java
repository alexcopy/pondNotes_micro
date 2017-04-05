package ru.m2mcom.pondnotes.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.pondnotes.domain.enumeration.DeviceType;

/**
 * A DTO for the Device entity.
 */
public class DeviceDTO implements Serializable {

    private Long id;

    @NotNull
    private String deviceName;

    @NotNull
    private DeviceType deviceType;

    private String description;

    private Integer timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
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

        DeviceDTO deviceDTO = (DeviceDTO) o;

        if ( ! Objects.equals(id, deviceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
            "id=" + id +
            ", deviceName='" + deviceName + "'" +
            ", deviceType='" + deviceType + "'" +
            ", description='" + description + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
