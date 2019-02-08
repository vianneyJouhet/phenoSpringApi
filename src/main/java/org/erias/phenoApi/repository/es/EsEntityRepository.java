package org.erias.phenoApi.repository.es;

import org.erias.phenoApi.model.Entity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsEntityRepository extends ElasticsearchRepository<Entity, String>{
	
}
