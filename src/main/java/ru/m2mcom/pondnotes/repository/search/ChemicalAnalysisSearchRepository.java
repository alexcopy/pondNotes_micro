package ru.m2mcom.pondnotes.repository.search;

import ru.m2mcom.pondnotes.domain.ChemicalAnalysis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ChemicalAnalysis entity.
 */
public interface ChemicalAnalysisSearchRepository extends ElasticsearchRepository<ChemicalAnalysis, Long> {
}
