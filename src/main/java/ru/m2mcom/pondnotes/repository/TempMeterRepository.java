package ru.m2mcom.pondnotes.repository;

import ru.m2mcom.pondnotes.domain.TempMeter;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TempMeter entity.
 */
@SuppressWarnings("unused")
public interface TempMeterRepository extends JpaRepository<TempMeter,Long> {

    @Query("select distinct tempMeter from TempMeter tempMeter left join fetch tempMeter.tanks")
    List<TempMeter> findAllWithEagerRelationships();

    @Query("select tempMeter from TempMeter tempMeter left join fetch tempMeter.tanks where tempMeter.id =:id")
    TempMeter findOneWithEagerRelationships(@Param("id") Long id);

}
