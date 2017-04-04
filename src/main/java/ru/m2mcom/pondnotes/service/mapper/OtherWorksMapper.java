package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.OtherWorksDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity OtherWorks and its DTO OtherWorksDTO.
 */
@Mapper(componentModel = "spring", uses = {TankMapper.class, })
public interface OtherWorksMapper {

    @Mapping(source = "tank.id", target = "tankId")
    @Mapping(source = "tank.tankName", target = "tankTankName")
    OtherWorksDTO otherWorksToOtherWorksDTO(OtherWorks otherWorks);

    List<OtherWorksDTO> otherWorksToOtherWorksDTOs(List<OtherWorks> otherWorks);

    @Mapping(source = "tankId", target = "tank")
    OtherWorks otherWorksDTOToOtherWorks(OtherWorksDTO otherWorksDTO);

    List<OtherWorks> otherWorksDTOsToOtherWorks(List<OtherWorksDTO> otherWorksDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default OtherWorks otherWorksFromId(Long id) {
        if (id == null) {
            return null;
        }
        OtherWorks otherWorks = new OtherWorks();
        otherWorks.setId(id);
        return otherWorks;
    }
    

}
