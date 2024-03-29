package org.erias.phenoApi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.IndexDocIdf;
import org.erias.phenoApi.model.JQCloud;
import org.erias.phenoApi.model.StatDocs;
import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.erias.phenoApi.repository.IndexDocIdfRepository;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.erias.phenoApi.repository.rdf4j.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JQCloudServiceImpl implements JQCloudService {

	protected static final Logger log = LogManager.getLogger(JQCloudServiceImpl.class);
	
	@Autowired
	private IndexDocIdfRepository  indexDocIdfRepo;
	@Autowired
	private IndexDocRepository  indexDocRepo;
	@Autowired
	private IndexDocIdfService idfService;
	@Autowired
	private HoomService hoomService;
	
	@Autowired
	private EntityRepository entityRepository;
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.JQCloudService#getJQCloud(java.lang.String)
	 */
	@Override
	public Map<String,Set<Object>>  getJQCloud(String cohorte) {
		
		List<IndexDocIdf> idDocIdfs =  (List<IndexDocIdf>) indexDocIdfRepo.findByCertaintyAndContextAndCohorteAndInferedMetrics("1", "patient_text",cohorte,false);
		log.info(idDocIdfs.size());
		long nbConceptTot = indexDocRepo.countByCertaintyAndContextAndCohorte("1", "patient_text",cohorte);
		return getJqCloudFromIndexDocIdfs(idDocIdfs, nbConceptTot);
	}
	
	@Override
	public Map<String,Set<Object>>  getAllJQCloud() {
		
		List<IndexDocIdf> idDocIdfs =  (List<IndexDocIdf>) indexDocIdfRepo.findByCertaintyAndContextAndInferedMetricsAndFrequencyGreaterThan("1", "patient_text",false,Long.parseLong("1"));
		log.info(idDocIdfs.size());
		long nbConceptTot = indexDocRepo.countByCertaintyAndContext("1", "patient_text");
		return getJqCloudFromIndexDocIdfs(idDocIdfs, nbConceptTot);
	}
	
	@Override
	public Map<String,Set<Object>>  getJQCloudByCodes(List<String> uris) {
		
		EntityHierarchie entityHierarchie = entityRepository.findSubClasses(uris.stream().collect(Collectors.toSet()));
		Set<String> patientNums = indexDocRepo.findByCertaintyAndContextAndCodeIn("1", "patient_text",entityHierarchie.getAllChilds())
				.stream().map(IndexDoc::getPatientNum).distinct().collect(Collectors.toSet());
		List<IndexDocIdf> idDocIdfs =  (List<IndexDocIdf>) indexDocIdfRepo
				.findByCertaintyAndContextAndInferedMetricsAndPatientNumIn("1", "patient_text",false,
				patientNums);
		log.info(idDocIdfs.size());
		long nbConceptTot = indexDocRepo.countByCertaintyAndContextAndPatientNumIn("1", "patient_text",patientNums);
		return getJqCloudFromIndexDocIdfs(idDocIdfs, nbConceptTot);
	}

	@Override
	public Map<String,Set<Object>>  getJQCloudAggByIcForGraph(String cohorte,Double icSanchez, String graph) {
		
		List<IndexDocIdf> idDocIdfs =idfService.findByIcInHierarchieInGraph(cohorte, icSanchez, graph);
		long nbConceptTot = indexDocRepo.countByCertaintyAndContextAndCohorte("1", "patient_text",cohorte);
		return getJqCloudFromIndexDocIdfs(idDocIdfs, nbConceptTot);
	}
	
	
	@Override
	public Map<String,Set<Object>>  getJQCloudByCodesAggByHoom(List<String> uris) {
		EntityHierarchie entityHierarchie = entityRepository.findSubClasses(uris.stream().collect(Collectors.toSet()));
		Set<String> patientNums = indexDocRepo.findByCertaintyAndContextAndCodeIn("1", "patient_text",entityHierarchie.getAllChilds())
				.stream().map(IndexDoc::getPatientNum).distinct().collect(Collectors.toSet());
		
		List<IndexDocIdf> idDocIdfs = hoomService.aggByHoom(uris.stream().collect(Collectors.toSet()),patientNums);
		return getJqCloudFromIndexDocIdfs(idDocIdfs, (long)idDocIdfs.size());
	}
	
	@Override
	public Map<String,Set<Object>>  getJQCloudAggByUrisAndIcForGraph(Set<String> uris,Double icSanchez, String graph) {
		
		EntityHierarchie entityHierarchie = entityRepository.findSubClasses(uris.stream().collect(Collectors.toSet()));
		Set<String> patientNums = indexDocRepo.findByCertaintyAndContextAndCodeIn("1", "patient_text",entityHierarchie.getAllChilds())
				.stream().map(IndexDoc::getPatientNum).distinct().collect(Collectors.toSet());
		List<IndexDocIdf> idDocIdfs =idfService.findByIcInHierarchieInGraph(patientNums, icSanchez, graph);
		long nbConceptTot = indexDocRepo.countByCertaintyAndContextAndPatientNumIn("1", "patient_text",patientNums);
		return getJqCloudFromIndexDocIdfs(idDocIdfs, nbConceptTot);
	}
	
	private Set<Object> CalculateTfJQCloudFromIndexDoc(List<IndexDocIdf> iDocIdfs,Map<String,String> labelMap){
		return iDocIdfs.stream()
		.filter(c -> c.getCertainty().equals("1") & c.getContext().equals("patient_text"))
		.map(c -> new IndexDoc(c.getPatientNum(), "", "", "", c.getCode(), "", "",""))
		.distinct()
		.collect(Collectors.groupingBy(IndexDoc::getCode,Collectors.counting()))
		.entrySet().stream()
		.map(e -> new JQCloud(
				e.getValue().floatValue(),
				labelMap.get(e.getKey()),
				new HashSet<String>(){/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

				{
					add(e.getKey());
					}}))
		.collect(Collectors.toSet());
	}
	
	private Set<Object> CalculateTfIdfJQCloudFromIndexDoc(List<IndexDocIdf> iDocIdfs,Map<String,String> labelMap,long nbConceptTot,Map<String,ThesaurusEnrsem> thesaurus){
		return iDocIdfs.stream()
				.collect(Collectors.groupingBy(IndexDocIdf::getCode,Collectors.counting()))
				.entrySet().stream()
				.map(e -> new JQCloud(
						(float)(((float)e.getValue()/nbConceptTot)*thesaurus.get(e.getKey()).getIdf()),
						labelMap.get(e.getKey()),
						new HashSet<String>(){/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

						{
							add(e.getKey());
							}}))
				.collect(Collectors.toSet());
	}
	
	private Set<Object> getTabFromIndexDocIdf(List<IndexDocIdf> iDocIdfs,long nbConceptTot,Map<String,ThesaurusEnrsem> thesaurus){
		Map<String, Long> idf = iDocIdfs.stream()
				.collect(Collectors.groupingBy(IndexDocIdf::getCode,Collectors.counting()));

		Map<String, Long> tf = iDocIdfs.stream()
				.filter(c -> c.getCertainty().equals("1") & c.getContext().equals("patient_text"))
				.map(c -> new IndexDoc(c.getPatientNum(), "", "", "", c.getCode(), "", "",""))
				.distinct()
				.collect(Collectors.groupingBy(IndexDoc::getCode,Collectors.counting()));
		
		return thesaurus.entrySet().stream()
				.map(t -> new ArrayList<String>(){/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

				{
					add(t.getValue().getCode());
					add(t.getValue().getLabel());
					add(Long.toString(tf.get(t.getValue().getCode())));
					add(Long.toString(idf.get(t.getValue().getCode())));
					add(Double.toString(t.getValue().getIdf()));
					add(
						Float.toString(
								(float)t.getValue().getIdf()*idf.get(t.getValue().getCode())/nbConceptTot
						)
					);
					add(
							t.getValue().getIcSanchez() != null? Double.toString(t.getValue().getIcSanchez()):"");
					}})
				.collect(Collectors.toSet());
	}
	
	private Map<String,Set<Object>> getJqCloudFromIndexDocIdfs(List<IndexDocIdf> idDocIdfs, Long nbConceptTot){
		Map<String, Set<Object>> jqclouds = new HashMap<>();
		idDocIdfs.forEach(i -> {
			if (i.getLabel()== null || i.getCode() == null) {
				log.info(i.toString());
			}
		});
		Map<String,String> labelMap=idDocIdfs.stream()
				.map(e -> new IndexDocIdf(e.getCode(), e.getLabel()))
				.distinct()		
				.collect(Collectors.toMap(IndexDocIdf::getCode, IndexDocIdf::getLabel));
		
		
		Map<String,ThesaurusEnrsem> thesaurus= idDocIdfs.stream()
				.map(t -> new ThesaurusEnrsem(t.getCode(), t.getFrequency(), t.getLabel(),(float)t.getIdf(),null,null,t.getIcSanchez()))
		.distinct()
		.collect(Collectors.toMap(ThesaurusEnrsem::getCode, t -> t));
		
		
		jqclouds.put("tf", CalculateTfJQCloudFromIndexDoc(idDocIdfs, labelMap));
		jqclouds.put("tfidf", CalculateTfIdfJQCloudFromIndexDoc(idDocIdfs, labelMap, nbConceptTot, thesaurus));
		jqclouds.put("tab", getTabFromIndexDocIdf(idDocIdfs, nbConceptTot, thesaurus));
		jqclouds.put("globalStat", getStatFromIndexDocIdf(idDocIdfs));
				
		return jqclouds;
		
	}
	
	private Set<Object> getStatFromIndexDocIdf(List<IndexDocIdf> idDocIdfs) {
		HashSet<Object> statDocs = new HashSet<Object>();
		StatDocs statDoc =new StatDocs();
		statDoc.setNbPat(idDocIdfs.stream().map(IndexDocIdf::getPatientNum).distinct().count());
		statDoc.setNbDocs(idDocIdfs.stream().map(IndexDocIdf::getDocumentNum).distinct().count());
		statDoc.setNbConcept(idDocIdfs.stream().count());
		statDocs.add(statDoc);
		return statDocs;
	}
}
