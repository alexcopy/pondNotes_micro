package ru.m2mcom.pondnotes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ru.m2mcom.pondnotes.domain.enumeration.DeviceType;

/**
 * A Device.
 */
@Entity
@Table(name = "device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private DeviceType deviceType;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "timestamp")
    private Integer timestamp;

    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(mappedBy = "device")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tank> tanks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Device deviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Device deviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDescription() {
        return description;
    }

    public Device description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Device timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public Device userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<Tank> getTanks() {
        return tanks;
    }

    public Device tanks(Set<Tank> tanks) {
        this.tanks = tanks;
        return this;
    }

    public Device addTank(Tank tank) {
        this.tanks.add(tank);
        tank.setDevice(this);
        return this;
    }

    public Device removeTank(Tank tank) {
        this.tanks.remove(tank);
        tank.setDevice(null);
        return this;
    }

    public void setTanks(Set<Tank> tanks) {
        this.tanks = tanks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        if (device.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Device{" +
            "id=" + id +
            ", deviceName='" + deviceName + "'" +
            ", deviceType='" + deviceType + "'" +
            ", description='" + description + "'" +
            ", timestamp='" + timestamp + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
