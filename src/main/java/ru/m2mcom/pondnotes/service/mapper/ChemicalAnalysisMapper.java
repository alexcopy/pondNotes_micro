package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.ChemicalAnalysisDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ChemicalAnalysis and its DTO ChemicalAnalysisDTO.
 */
@Mapper(componentModel = "spring", uses = {TankMapper.class, })
public interface ChemicalAnalysisMapper {

    ChemicalAnalysisDTO chemicalAnalysisToChemicalAnalysisDTO(ChemicalAnalysis chemicalAnalysis);

    List<ChemicalAnalysisDTO> chemicalAnalysesToChemicalAnalysisDTOs(List<ChemicalAnalysis> chemicalAnalyses);

    ChemicalAnalysis chemicalAnalysisDTOToChemicalAnalysis(ChemicalAnalysisDTO chemicalAnalysisDTO);

    List<ChemicalAnalysis> chemicalAnalysisDTOsToChemicalAnalyses(List<ChemicalAnalysisDTO> chemicalAnalysisDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default ChemicalAnalysis chemicalAnalysisFromId(Long id) {
        if (id == null) {
            return null;
        }
        ChemicalAnalysis chemicalAnalysis = new ChemicalAnalysis();
        chemicalAnalysis.setId(id);
        return chemicalAnalysis;
    }
    

}
