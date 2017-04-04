package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.RegisteredUserDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity RegisteredUser and its DTO RegisteredUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegisteredUserMapper {

    RegisteredUserDTO registeredUserToRegisteredUserDTO(RegisteredUser registeredUser);

    List<RegisteredUserDTO> registeredUsersToRegisteredUserDTOs(List<RegisteredUser> registeredUsers);

    RegisteredUser registeredUserDTOToRegisteredUser(RegisteredUserDTO registeredUserDTO);

    List<RegisteredUser> registeredUserDTOsToRegisteredUsers(List<RegisteredUserDTO> registeredUserDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default RegisteredUser registeredUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId(id);
        return registeredUser;
    }
    

}
