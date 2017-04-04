package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.MeterReadingDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MeterReading and its DTO MeterReadingDTO.
 */
@Mapper(componentModel = "spring", uses = {TankMapper.class, })
public interface MeterReadingMapper {

    @Mapping(source = "tank.id", target = "tankId")
    @Mapping(source = "tank.tankName", target = "tankTankName")
    MeterReadingDTO meterReadingToMeterReadingDTO(MeterReading meterReading);

    List<MeterReadingDTO> meterReadingsToMeterReadingDTOs(List<MeterReading> meterReadings);

    @Mapping(source = "tankId", target = "tank")
    MeterReading meterReadingDTOToMeterReading(MeterReadingDTO meterReadingDTO);

    List<MeterReading> meterReadingDTOsToMeterReadings(List<MeterReadingDTO> meterReadingDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default MeterReading meterReadingFromId(Long id) {
        if (id == null) {
            return null;
        }
        MeterReading meterReading = new MeterReading();
        meterReading.setId(id);
        return meterReading;
    }
    

}
