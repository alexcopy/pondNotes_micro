package ru.m2mcom.pondnotes.repository.search;

import ru.m2mcom.pondnotes.domain.FilterPumpCleaning;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FilterPumpCleaning entity.
 */
public interface FilterPumpCleaningSearchRepository extends ElasticsearchRepository<FilterPumpCleaning, Long> {
}
