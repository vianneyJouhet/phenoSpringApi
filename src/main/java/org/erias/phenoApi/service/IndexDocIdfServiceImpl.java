package org.erias.phenoApi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.IndexDocIdf;
import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.erias.phenoApi.repository.IndexDocIdfRepository;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.erias.phenoApi.repository.rdf4j.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class IndexDocIdfServiceImpl implements IndexDocIdfService {

	protected static final Logger log = LogManager.getLogger(IndexDocIdfServiceImpl.class);
	
	@Autowired
	private IndexDocIdfRepository  indexDocIdfRepo;
	@Autowired 
	private EntityRepository entityRepository;
	@Autowired 
	private ThesaurusEnrsemService thesaurusEnrsemService;
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.IndexDocIdfService#findByIcInHierarchieInGraph(java.lang.String, java.lang.Double, java.lang.String)
	 */
	@Override
	public List<IndexDocIdf> findByIcInHierarchieInGraph(String cohorte,Double icSanchez,String graph){
		
		EntityHierarchie entityHierarchie=entityRepository.findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(graph, icSanchez).setDeepestAncestors();
		Map<String,ThesaurusEnrsem> thesaurusEnrsems = thesaurusEnrsemService.findEnrsemInferredByCodesIn(entityHierarchie.getAllAncestors(), graph);
		log.info(thesaurusEnrsems.size());
		return findByCodesInHierarchie(entityHierarchie,thesaurusEnrsems,cohorte);
	}
	
	@Override
	public List<IndexDocIdf> findByIcInHierarchieInGraph(Set<String> patientNums,Double icSanchez,String graph){
		
		EntityHierarchie entityHierarchie=entityRepository.findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(graph, icSanchez).setDeepestAncestors();
		Map<String,ThesaurusEnrsem> thesaurusEnrsems = thesaurusEnrsemService.findEnrsemInferredByCodesIn(entityHierarchie.getAllAncestors(), graph);
		log.info(thesaurusEnrsems.size());
		return findByCodesInHierarchie(entityHierarchie,thesaurusEnrsems,patientNums);
	}
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.IndexDocIdfService#findByCodesInHierarchie(org.erias.phenoApi.model.EntityHierarchie, java.util.Map, java.lang.String)
	 */
	@Override
	public List<IndexDocIdf> findByCodesInHierarchie(EntityHierarchie entityHierarchie,Map<String,ThesaurusEnrsem> thesaurusEnrsems,String cohorte){
		StopWatch sw = new StopWatch();
		sw.start("retrieve data");
		List<IndexDocIdf> IndexDocIdfAgg = new ArrayList<IndexDocIdf>();
		List<IndexDocIdf> indexdocIdf=indexDocIdfRepo.findByCertaintyAndContextAndCohorteAndCodeIn("1", "patient_text",cohorte,entityHierarchie.getAllChilds());
		sw.stop();
		sw.start("aggregate data");
		indexdocIdf
		.stream()
		.forEach(iDocAgg -> {
			entityHierarchie.getDeepestAncestors(iDocAgg.getCode()).forEach(ancestor -> {
					log.info(ancestor);
//						log.info(ancestor);
//						log.info(thesaurusEnrsems.get(ancestor));
						iDocAgg.setCode(ancestor);
						iDocAgg.setFrequency(thesaurusEnrsems.get(ancestor).getFrequency());
						iDocAgg.setIdf(thesaurusEnrsems.get(ancestor).getIdf());
						iDocAgg.setLabel(thesaurusEnrsems.get(ancestor).getLabel());
						IndexDocIdfAgg.add(iDocAgg);
			});
		});
		sw.stop();
		log.info(sw.prettyPrint());
		return IndexDocIdfAgg;
	}
	
	@Override
	public List<IndexDocIdf> findByPatientNumAndCodesInHierarchie(EntityHierarchie entityHierarchie,Set<String> patientNums){
		StopWatch sw = new StopWatch();
		sw.start("retrieve data");
		
		Map<String,ThesaurusEnrsem> thesaurusEnrsems = thesaurusEnrsemService.findEnrsemInferredByCodesIn(entityHierarchie.getAllAncestors(),"http://erias.org/hpo");
		List<IndexDocIdf> IndexDocIdfAgg = new ArrayList<IndexDocIdf>();
		List<IndexDocIdf> indexdocIdf =  (List<IndexDocIdf>) indexDocIdfRepo
				.findByCertaintyAndContextAndInferedMetricsAndPatientNumIn("1", "patient_text",false,
				patientNums);
		log.info(patientNums.size());
		sw.stop();
		sw.start("aggregate data");
		
		indexdocIdf
		.stream()
		.forEach(iDocAgg -> {
			if (entityHierarchie.getAllChilds().contains(iDocAgg.getCode())){
				entityHierarchie.getDeepestAncestors(iDocAgg.getCode()).forEach(ancestor -> {
//					log.info(ancestor);
//						log.info(ancestor);
//						log.info(thesaurusEnrsems.get(ancestor));
						iDocAgg.setCode(ancestor);
						iDocAgg.setFrequency(thesaurusEnrsems.get(ancestor).getFrequency());
						iDocAgg.setIdf(thesaurusEnrsems.get(ancestor).getIdf());
						iDocAgg.setLabel(thesaurusEnrsems.get(ancestor).getLabel());	
				});
			}
			IndexDocIdfAgg.add(iDocAgg);
		});
		sw.stop();
		log.info(sw.prettyPrint());
		return IndexDocIdfAgg;
	}
	
	@Override
	public List<IndexDocIdf> findByCodesInHierarchie(EntityHierarchie entityHierarchie,Map<String,ThesaurusEnrsem> thesaurusEnrsems,Set<String> patientNums){
		StopWatch sw = new StopWatch();
		sw.start("retrieve data");
		List<IndexDocIdf> IndexDocIdfAgg = new ArrayList<IndexDocIdf>();
		List<IndexDocIdf> indexdocIdf=indexDocIdfRepo.findByCertaintyAndContextAndPatientNumInAndCodeIn("1", "patient_text",patientNums,entityHierarchie.getAllChilds());
		sw.stop();
		sw.start("aggregate data");
		indexdocIdf
		.stream()
		.forEach(iDocAgg -> {
			entityHierarchie.getDeepestAncestors(iDocAgg.getCode()).forEach(ancestor -> {
					log.info(ancestor);
//						log.info(ancestor);
//						log.info(thesaurusEnrsems.get(ancestor));
						iDocAgg.setCode(ancestor);
						iDocAgg.setFrequency(thesaurusEnrsems.get(ancestor).getFrequency());
						iDocAgg.setIdf(thesaurusEnrsems.get(ancestor).getIdf());
						iDocAgg.setLabel(thesaurusEnrsems.get(ancestor).getLabel());
						IndexDocIdfAgg.add(iDocAgg);
			});
		});
		sw.stop();
		log.info(sw.prettyPrint());
		return IndexDocIdfAgg;
	}
	
}
