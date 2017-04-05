package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.TempMeterDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TempMeter and its DTO TempMeterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TempMeterMapper {

    TempMeterDTO tempMeterToTempMeterDTO(TempMeter tempMeter);

    List<TempMeterDTO> tempMetersToTempMeterDTOs(List<TempMeter> tempMeters);

    TempMeter tempMeterDTOToTempMeter(TempMeterDTO tempMeterDTO);

    List<TempMeter> tempMeterDTOsToTempMeters(List<TempMeterDTO> tempMeterDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TempMeter tempMeterFromId(Long id) {
        if (id == null) {
            return null;
        }
        TempMeter tempMeter = new TempMeter();
        tempMeter.setId(id);
        return tempMeter;
    }
    

}
