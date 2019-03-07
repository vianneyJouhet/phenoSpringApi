package org.erias.phenoApi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.erias.phenoApi.model.Entity;
import org.erias.phenoApi.model.EntityInGraph;
import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.erias.phenoApi.repository.ThesaurusEnrsemRepository;
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

	@Autowired
	private  ThesaurusEnrsemRepository thesaurusEnrsemRepository;

	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.EsEntityService#updateEntityRepository()
	 */
	@Override 
	public void updateEntityRepository() {
		esEntityRepository.deleteAll();
		Map<String,Long> foundCodes = thesaurusEnrsemRepository.findByinferedMetricsAndFrequencyGreaterThanEqual(true,Long.parseLong("1"))
		.stream()
		.collect(Collectors.toMap(ThesaurusEnrsem::getCode, ThesaurusEnrsem::getFrequency));
		Map<String,Set<String>> urisIngraphs = entityRepository.findAllUrisInThesaurusGraph();
		esEntityRepository.saveAll(entityRepository.findAllEntity()
				.stream()
				.filter(e -> foundCodes.keySet().contains(e.getUri()))
				.map(e -> new Entity(e,foundCodes.get(e.getUri()),urisIngraphs.get(e.getUri())))
				.collect(Collectors.toSet()));
	}
}
