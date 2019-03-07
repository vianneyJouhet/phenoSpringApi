package org.erias.phenoApi.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.EntityInGraph;
import org.erias.phenoApi.model.ICFeature;
import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.InformationContent;
import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.erias.phenoApi.repository.IndexDocIdfRepository;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.erias.phenoApi.repository.ThesaurusEnrsemRepository;
import org.erias.phenoApi.repository.rdf4j.EntityRepository;
import org.erias.phenoApi.repository.rdf4j.GraphRepository;
import org.erias.phenoApi.repository.rdf4j.ICFeatureRepository;
import org.erias.phenoApi.repository.rdf4j.ICFeatureRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class ThesaurusEnrsemServiceImpl implements ThesaurusEnrsemService {
	
	protected static final Logger log = LogManager.getLogger(ThesaurusEnrsemServiceImpl.class);

	
	
	@Autowired
	private IndexDocRepository indexDocRepository;
	
	@Autowired
	private IndexDocIdfRepository indexDocIdfRepository;
	
	@Autowired
	private ThesaurusEnrsemRepository thesaurusEnrsemRepository;
	
	@Autowired
	private GraphRepository graphRepository;
	
	@Autowired
	private EntityInGraphService entityInGraphService;
	
	@Autowired
	private ICFeatureRepository icfeaturerepo;
	
	@Autowired 
	private EntityRepository entityRepository;
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.ThesaurusEnrsemService#loadThesaurusFromIndexDoc()
	 */
	
	@Override
	public List<ThesaurusEnrsem> loadThesaurusFromIndexDoc() {
		log.info("get code aggregate ");
		
		return thesaurusEnrsemRepository.findCodeCount();
	}
	

	@Override
	public void updateThesaurusEnrSem() {
		StopWatch sw =new StopWatch();
		sw.start("load direct");
		thesaurusEnrsemRepository.deleteAll();
		Map<String, Long> thesaurusFrequencies = thesaurusEnrsemRepository.findCodeCount()
				.stream()
				.collect(Collectors.toMap(ThesaurusEnrsem::getCode, ThesaurusEnrsem::getFrequency));
		
		Long nbPat = indexDocRepository.countDistinctPatientNum();
		Set<String> graphs =graphRepository.findAllThesaurusGraph();
				
				thesaurusEnrsemRepository.batchLoad(10000, 
				entityInGraphService.getAllEntityInGraph(graphs)
				.stream()
				.map(e -> new ThesaurusEnrsem(e.getUri(), thesaurusFrequencies.get(e.getUri())==null?0:thesaurusFrequencies.get(e.getUri()), e.getPrefLabel(), nbPat))
				.collect(Collectors.toSet())
			); 
		sw.stop();
		
		sw.start("load inferred");
		updateInferredMetricsForConcepts(nbPat);
		sw.stop();
		log.info(sw.prettyPrint());		
	}
	
	private void updateInferredMetricsForConcepts(Long nbPat) {
		log.info("processing infered Metrics");
		Set<String> graphs =graphRepository.findAllStructuralGraph();
		
		graphs.forEach(g -> {
			log.info("graph ==> " + g);
			Map<String, Long> thesaurusFrequencies = new HashMap<String,Long>();
			
			EntityHierarchie entityHierarchie = entityRepository.findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(g, Double.parseDouble("1"));
			thesaurusFrequencies.putAll(indexDocIdfRepository.findByCertaintyAndContextAndCodeInAndInferedMetrics("1", "patient_text", entityHierarchie.getAllChilds(), false)
			.stream()
			.flatMap( e -> 
				entityHierarchie.getAncestors(e.getCode(), true)
				.stream()
				.map(a -> new IndexDoc(e.getPatientNum(), "", "", "", a, "", "",""))
				)
				.distinct()
				.collect(Collectors.groupingBy(IndexDoc::getCode,Collectors.counting())));

//			entityHierarchie.getAllAncestors().parallelStream().forEach(a -> {
//				thesaurusFrequencies.putAll(indexDocIdfRepository.findByCertaintyAndContextAndCodeInAndInferedMetrics("1", "patient_text", entityHierarchie.getChilds(a, true), false)
//				.stream()
//				.map(c -> new IndexDoc(c.getPatientNum(), "", "", "", a, "", "",""))
//				.distinct()
//				.collect(Collectors.groupingBy(IndexDoc::getCode,Collectors.counting())));
//			});
			log.info("Loading for graph ==> " + g);
			thesaurusEnrsemRepository.batchLoad(10000, 
				entityInGraphService.getAllEntityInGraph(g)
				.stream()
				.map(e -> new ThesaurusEnrsem(e.getUri(), thesaurusFrequencies.get(e.getUri())==null?0:thesaurusFrequencies.get(e.getUri()), e.getPrefLabel(), nbPat,true,g))
				.collect(Collectors.toSet())
			);
		});
		
		
	}
	
	@Override
	public void loadThesaurusEnrSemFromIndexDoc(String protocol,int port,String url,String namespace) {
		
		log.info("get code aggregate ");
		
		
		List<ThesaurusEnrsem> thesaurusFrequencies = thesaurusEnrsemRepository.findCodeCount();
		LabelMapper labelMapper = new LabelMapperImpl(protocol, url, port, namespace);
		
		HashMap<String, String> labels = labelMapper.getPrefLabelForUrisBatch(
				thesaurusFrequencies.stream().map(ThesaurusEnrsem::getCode).collect(Collectors.toSet()));

	
//		Set<ICFeature> icFeature = icfeaturerepo.getALLICFeatureByGraph("http://erias.org/hpo");
//		Long maxLeaves = icFeature
//				.stream()
//				.map(t -> t.getLeaves())
//				.max((t1,t2) -> Long.compare(t1, t2))
//				.get();
		Long nbPat = indexDocRepository.countDistinctPatientNum();
//		HashMap<String, InformationContent> icContent= new HashMap<String, InformationContent>();
//		icFeature.forEach(ic -> {
//			icContent.put(ic.getUri(), new InformationContent(ic, maxLeaves) );
//		});
			
					
		log.info("Nombre de patient total " + nbPat);
		log.info("Number of codes ==> " + thesaurusFrequencies.size());
		log.info("Number of labels ==> " + labels.size());
		thesaurusEnrsemRepository.batchLoad(10000,
				thesaurusFrequencies.stream()
						.filter(t -> labels.get(t.getCode())!=(null))
						.map(t -> new ThesaurusEnrsem(t.getCode(), t.getFrequency(), labels.get(t.getCode()),nbPat))
						.collect(Collectors.toSet()));
		
	}

	@Override
	public Set<ThesaurusEnrsem> findEnrsemByCodesInHierarchie(Set<String> codes,String Graph){
		
		EntityHierarchie entityHierarchie=entityRepository.findSubClasses(codes).setDeepestAncestors();
		return findEnrsemByCodesInHierarchie(entityHierarchie).values().stream().collect(Collectors.toSet());
	}
	
	@Override
	public Set<ThesaurusEnrsem> findEnrsemByIcInHierarchieInGraph(Double icSanchez,String graph){
		
		EntityHierarchie entityHierarchie=entityRepository.findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(graph, icSanchez).setDeepestAncestors();
		return findEnrsemByCodesInHierarchie(entityHierarchie).values().stream().collect(Collectors.toSet());
	}
	
	@Override
	public Map<String, ThesaurusEnrsem> findEnrsemByCodesInHierarchie(EntityHierarchie entityHierarchie){
			Map<String,ThesaurusEnrsem> thesaurusEnrsemAgg = new HashMap<String,ThesaurusEnrsem>();
			Long nbPat = indexDocRepository.countDistinctPatientNum();
		Map<String,ThesaurusEnrsem> thesaurusEnrsems =thesaurusEnrsemRepository.findByCodeIn(entityHierarchie.getAllChilds())
				.stream().collect(Collectors.toMap(ThesaurusEnrsem::getCode, x -> x));; 
				
				thesaurusEnrsems.entrySet()
					.stream()
					.map(Entry::getValue)
					.forEach( th -> {
						entityHierarchie.getDeepestAncestors(th.getCode()).forEach(ancestor -> {
							if (thesaurusEnrsemAgg.containsKey(ancestor)) {
								ThesaurusEnrsem thAgg =thesaurusEnrsemAgg.get(ancestor);
								thAgg.setFrequency(thesaurusEnrsemAgg.get(ancestor).getFrequency()+th.getFrequency());
								thAgg.calculateIdf(nbPat);	
								thesaurusEnrsemAgg.put(ancestor, thAgg);
							}else{
								th.setCode(ancestor);
								th.setLabel(thesaurusEnrsems.get(ancestor).getLabel());
								thesaurusEnrsemAgg.put(ancestor, th);
							};
						});
					});
		return thesaurusEnrsemAgg;
	}
	
	@Override
	public Map<String, ThesaurusEnrsem> findEnrsemInferredByCodesIn(Set<String> codes,String graph){
		Map<String,ThesaurusEnrsem> thesaurusEnrsems =thesaurusEnrsemRepository.findByInferedMetricsAndGraphAndCodeIn(true, graph, codes)
				.stream().collect(Collectors.toMap(ThesaurusEnrsem::getCode, x -> x));
				
				
		return thesaurusEnrsems;
	}
	
	@Override
	public Map<String, ThesaurusEnrsem> findEnrsemInferredByCodesIn(Set<String> codes){
		Map<String,ThesaurusEnrsem> thesaurusEnrsems =thesaurusEnrsemRepository.findByInferedMetricsAndCodeIn(true, codes)
				.stream().collect(Collectors.toMap(ThesaurusEnrsem::getCode, x -> x));
				
				
		return thesaurusEnrsems;
	}
}
