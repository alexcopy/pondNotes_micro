package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.ChemicalsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Chemicals and its DTO ChemicalsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChemicalsMapper {

    ChemicalsDTO chemicalsToChemicalsDTO(Chemicals chemicals);

    List<ChemicalsDTO> chemicalsToChemicalsDTOs(List<Chemicals> chemicals);

    Chemicals chemicalsDTOToChemicals(ChemicalsDTO chemicalsDTO);

    List<Chemicals> chemicalsDTOsToChemicals(List<ChemicalsDTO> chemicalsDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Chemicals chemicalsFromId(Long id) {
        if (id == null) {
            return null;
        }
        Chemicals chemicals = new Chemicals();
        chemicals.setId(id);
        return chemicals;
    }
    

}
