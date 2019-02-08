package org.erias.phenoApi.service;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.EntityInGraph;
import org.erias.phenoApi.model.ICFeature;
import org.erias.phenoApi.model.InformationContent;
import org.erias.phenoApi.repository.rdf4j.EntityRepository;
import org.erias.phenoApi.repository.rdf4j.GraphRepository;
import org.erias.phenoApi.repository.rdf4j.ICFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityInGraphServiceImpl implements  EntityInGraphService {

	protected static final Logger log = LogManager.getLogger(EntityInGraphServiceImpl.class);
	
	@Autowired 
	private EntityRepository entityRepository;
	
	@Autowired 
	private ICFeatureRepository icFeatureRepository;
	
	@Autowired 
	private GraphRepository graphRepository;

	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.EntityInGraph#getAllEntityInGraph(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.EntityInGraphService#getAllEntityInGraph(java.lang.String)
	 */
	@Override
	public Set<EntityInGraph> getAllEntityInGraph(String graph ){
		return entityRepository.findAllEntityInGraph(graph);
	}
	
	@Override
	public Set<EntityInGraph> getAllEntityInGraph(Set<String> graph ){
		return entityRepository.findAllEntityInGraph(graph);
	}
	
	@Override
	public Set<EntityInGraph> getEntityByIcSanchezLessThanInGraph(String graph,Double icSanchez ){
		return entityRepository.findAllEntityByIcSanchezLowerOrEqualThanInGraph(graph, icSanchez);
	}
	
	@Override
	public void updateIC(InformationContent ic){
//		log.info(ic.toString());
		entityRepository.updateMetricsForEntity(ic);
	}
	
	@Override
	public void updateIC(Set<InformationContent> ic){
//		log.info(ic.toString());
		entityRepository.updateMetricsForEntity(ic);
	}
	
	@Override
	public void computeGraphMetrics(String graph) {
		HashMap<String, InformationContent> icContent= new HashMap<String, InformationContent>();
		Set<ICFeature> icFeatures=icFeatureRepository.getALLICFeatureByGraph(graph);
		if (icFeatures.size()> 0) {
			Long maxLeaves = icFeatures
					.stream()
					.map(t -> t.getLeaves())
					.max((t1,t2) -> Long.compare(t1, t2))
					.get();
			
			
			updateIC(icFeatures
					.stream()
					.map(ic -> new InformationContent(ic, maxLeaves))
					.collect(Collectors.toSet()));
//			.forEach(ic -> {
//	//			log.info(ic.toString());
//				entityInGraphService.updateIC(ic);
//	//			icContent.put(ic.getUri(), new InformationContent(ic, maxLeaves) );
//			});
			
		}
	}
	
	@Override
	public void updateGraphMetrics() {
		graphRepository.findAllStructuralGraph().forEach(g ->{
			computeGraphMetrics(g);;	
	});
	}

}
