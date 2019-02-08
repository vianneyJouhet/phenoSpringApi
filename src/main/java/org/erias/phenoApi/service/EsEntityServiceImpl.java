package org.erias.phenoApi.service;

import java.util.stream.Collectors;

import org.erias.phenoApi.model.Entity;
import org.erias.phenoApi.repository.es.EsEntityRepository;
import org.erias.phenoApi.repository.rdf4j.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsEntityServiceImpl implements EsEntityService {
	@Autowired
	private EsEntityRepository esEntityRepository;
	
	@Autowired
	private EntityRepository entityRepository;
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.EsEntityService#updateEntityRepository()
	 */
	@Override 
	public void updateEntityRepository() {
		
		esEntityRepository.saveAll(entityRepository.findAllEntity()
				.stream()
				.map(e -> new Entity(e))
				.collect(Collectors.toSet()));
	}
}
