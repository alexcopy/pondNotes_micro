package ru.m2mcom.pondnotes.repository;

import ru.m2mcom.pondnotes.domain.LiveStock;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LiveStock entity.
 */
@SuppressWarnings("unused")
public interface LiveStockRepository extends JpaRepository<LiveStock,Long> {

}
