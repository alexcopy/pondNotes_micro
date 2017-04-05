package ru.m2mcom.pondnotes.service.mapper;

import ru.m2mcom.pondnotes.domain.*;
import ru.m2mcom.pondnotes.service.dto.LiveStockDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LiveStock and its DTO LiveStockDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LiveStockMapper {

    LiveStockDTO liveStockToLiveStockDTO(LiveStock liveStock);

    List<LiveStockDTO> liveStocksToLiveStockDTOs(List<LiveStock> liveStocks);

    LiveStock liveStockDTOToLiveStock(LiveStockDTO liveStockDTO);

    List<LiveStock> liveStockDTOsToLiveStocks(List<LiveStockDTO> liveStockDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default LiveStock liveStockFromId(Long id) {
        if (id == null) {
            return null;
        }
        LiveStock liveStock = new LiveStock();
        liveStock.setId(id);
        return liveStock;
    }
    

}
