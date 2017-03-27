package ru.m2mcom.pondnotes.repository.search;

import ru.m2mcom.pondnotes.domain.LiveStock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LiveStock entity.
 */
public interface LiveStockSearchRepository extends ElasticsearchRepository<LiveStock, Long> {
}
