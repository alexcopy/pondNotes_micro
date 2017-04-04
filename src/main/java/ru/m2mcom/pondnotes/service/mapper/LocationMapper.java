package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.LocationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {RegisteredUserMapper.class, })
public interface LocationMapper {

    @Mapping(source = "registereduser.id", target = "registereduserId")
    @Mapping(source = "registereduser.userName", target = "registereduserUserName")
    LocationDTO locationToLocationDTO(Location location);

    List<LocationDTO> locationsToLocationDTOs(List<Location> locations);

    @Mapping(source = "registereduserId", target = "registereduser")
    Location locationDTOToLocation(LocationDTO locationDTO);

    List<Location> locationDTOsToLocations(List<LocationDTO> locationDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
    

}
