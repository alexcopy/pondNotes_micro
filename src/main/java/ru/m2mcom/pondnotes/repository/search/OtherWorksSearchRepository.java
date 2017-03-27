package ru.m2mcom.pondnotes.repository.search;

import ru.m2mcom.pondnotes.domain.OtherWorks;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OtherWorks entity.
 */
public interface OtherWorksSearchRepository extends ElasticsearchRepository<OtherWorks, Long> {
}
