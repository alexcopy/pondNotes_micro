package ru.m2mcom.pondnotes.repository;

import ru.m2mcom.pondnotes.domain.WaterChange;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WaterChange entity.
 */
@SuppressWarnings("unused")
public interface WaterChangeRepository extends JpaRepository<WaterChange,Long> {

}
