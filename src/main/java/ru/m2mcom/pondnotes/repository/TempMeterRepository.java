package ru.m2mcom.pondnotes.repository;

import ru.m2mcom.pondnotes.domain.TempMeter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TempMeter entity.
 */
@SuppressWarnings("unused")
public interface TempMeterRepository extends JpaRepository<TempMeter,Long> {

}
