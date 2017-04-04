package ru.m2mcom.pondnotes.repository.search;

import ru.m2mcom.pondnotes.domain.RegisteredUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RegisteredUser entity.
 */
public interface RegisteredUserSearchRepository extends ElasticsearchRepository<RegisteredUser, Long> {
}
