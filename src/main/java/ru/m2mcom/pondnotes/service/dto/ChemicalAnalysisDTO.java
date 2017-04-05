package ru.m2mcom.pondnotes.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ChemicalAnalysis entity.
 */
public class ChemicalAnalysisDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;

    private String nO2;

    private String nO3;

    private String nH4;

    private String ph;

    @NotNull
    private Double tempVal;

    private Integer timestamp;

    @Lob
    private byte[] picture;
    private String pictureContentType;

    private String description;

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
    public String getnO2() {
        return nO2;
    }

    public void setnO2(String nO2) {
        this.nO2 = nO2;
    }
    public String getnO3() {
        return nO3;
    }

    public void setnO3(String nO3) {
        this.nO3 = nO3;
    }
    public String getnH4() {
        return nH4;
    }

    public void setnH4(String nH4) {
        this.nH4 = nH4;
    }
    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
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
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
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

        ChemicalAnalysisDTO chemicalAnalysisDTO = (ChemicalAnalysisDTO) o;

        if ( ! Objects.equals(id, chemicalAnalysisDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChemicalAnalysisDTO{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", nO2='" + nO2 + "'" +
            ", nO3='" + nO3 + "'" +
            ", nH4='" + nH4 + "'" +
            ", ph='" + ph + "'" +
            ", tempVal='" + tempVal + "'" +
            ", timestamp='" + timestamp + "'" +
            ", picture='" + picture + "'" +
            ", description='" + description + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
