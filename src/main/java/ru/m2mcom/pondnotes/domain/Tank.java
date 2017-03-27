package ru.m2mcom.pondnotes.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import ru.m2mcom.pondnotes.domain.enumeration.TankType;

/**
 * A Tank.
 */
@Entity
@Table(name = "tank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tank")
public class Tank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tank_name", nullable = false)
    private String tankName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tank_type", nullable = false)
    private TankType tankType;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Integer timestamp;

    @ManyToOne
    private Location location;

    @ManyToOne
    private Device device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTankName() {
        return tankName;
    }

    public Tank tankName(String tankName) {
        this.tankName = tankName;
        return this;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }

    public TankType getTankType() {
        return tankType;
    }

    public Tank tankType(TankType tankType) {
        this.tankType = tankType;
        return this;
    }

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }

    public String getDescription() {
        return description;
    }

    public Tank description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Tank timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public Tank location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Device getDevice() {
        return device;
    }

    public Tank device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tank tank = (Tank) o;
        if (tank.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tank{" +
            "id=" + id +
            ", tankName='" + tankName + "'" +
            ", tankType='" + tankType + "'" +
            ", description='" + description + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
