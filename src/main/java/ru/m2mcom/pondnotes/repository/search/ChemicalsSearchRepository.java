package ru.m2mcom.pondnotes.repository.search;

import ru.m2mcom.pondnotes.domain.Chemicals;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Chemicals entity.
 */
public interface ChemicalsSearchRepository extends ElasticsearchRepository<Chemicals, Long> {
}
