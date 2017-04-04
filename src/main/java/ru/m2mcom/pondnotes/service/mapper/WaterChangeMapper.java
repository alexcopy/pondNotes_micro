package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.WaterChangeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WaterChange and its DTO WaterChangeDTO.
 */
@Mapper(componentModel = "spring", uses = {TankMapper.class, })
public interface WaterChangeMapper {

    @Mapping(source = "tank.id", target = "tankId")
    @Mapping(source = "tank.tankName", target = "tankTankName")
    WaterChangeDTO waterChangeToWaterChangeDTO(WaterChange waterChange);

    List<WaterChangeDTO> waterChangesToWaterChangeDTOs(List<WaterChange> waterChanges);

    @Mapping(source = "tankId", target = "tank")
    WaterChange waterChangeDTOToWaterChange(WaterChangeDTO waterChangeDTO);

    List<WaterChange> waterChangeDTOsToWaterChanges(List<WaterChangeDTO> waterChangeDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default WaterChange waterChangeFromId(Long id) {
        if (id == null) {
            return null;
        }
        WaterChange waterChange = new WaterChange();
        waterChange.setId(id);
        return waterChange;
    }
    

}
