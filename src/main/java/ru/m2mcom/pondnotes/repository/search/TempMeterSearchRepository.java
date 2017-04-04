package ru.m2mcom.pondnotes.repository.search;

import ru.m2mcom.pondnotes.domain.TempMeter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TempMeter entity.
 */
public interface TempMeterSearchRepository extends ElasticsearchRepository<TempMeter, Long> {
}
