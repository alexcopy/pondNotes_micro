package ru.m2mcom.pondnotes.repository.search;

import ru.m2mcom.pondnotes.domain.Tank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tank entity.
 */
public interface TankSearchRepository extends ElasticsearchRepository<Tank, Long> {
}
