package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.DeviceService;
import ru.m2mcom.pondnotes.domain.Device;
import ru.m2mcom.pondnotes.repository.DeviceRepository;
import ru.m2mcom.pondnotes.repository.search.DeviceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Device.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService{

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);
    
    private final DeviceRepository deviceRepository;

    private final DeviceSearchRepository deviceSearchRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceSearchRepository deviceSearchRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceSearchRepository = deviceSearchRepository;
    }

    /**
     * Save a device.
     *
     * @param device the entity to save
     * @return the persisted entity
     */
    @Override
    public Device save(Device device) {
        log.debug("Request to save Device : {}", device);
        Device result = deviceRepository.save(device);
        deviceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the devices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        Page<Device> result = deviceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one device by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Device findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        Device device = deviceRepository.findOne(id);
        return device;
    }

    /**
     *  Delete the  device by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.delete(id);
        deviceSearchRepository.delete(id);
    }

    /**
     * Search for the device corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Device> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Devices for query {}", query);
        Page<Device> result = deviceSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
