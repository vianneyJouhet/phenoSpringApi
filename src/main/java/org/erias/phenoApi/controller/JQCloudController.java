package org.erias.phenoApi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.server.DatadirCleanupManager.PurgeTaskStatus;
import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.IndexDocIdf;
import org.erias.phenoApi.model.JQCloud;
import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.erias.phenoApi.repository.IndexDocIdfRepository;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.erias.phenoApi.service.LabelMapper;
import org.jline.terminal.impl.jna.freebsd.CLibrary.termios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JQCloudController {

	protected static final Logger log = LogManager.getLogger(JQCloudController.class);
	
//	private SQLConnector psClient; 
	
	@Autowired
	private IndexDocIdfRepository  indexDocIdfRepo;
	@Autowired
	private IndexDocRepository  indexDocRepo;
	
	private LabelMapper labelMapper;
	
	public JQCloudController(
			@Value("${sparql.protocol}") String sparqlProtocol,
			@Value("${sparql.port}")int sparqlPort,
			@Value("${sparql.domain}") String sparqlUrl,
			@Value("${sparql.endpoint}")String sparqlNamespace) {
		// TODO Auto-generated constructor stub
		
		this.labelMapper = new LabelMapper(sparqlProtocol, sparqlUrl, sparqlPort, sparqlNamespace);
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping(value = "/jq-cloud",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloud(@RequestBody(required=true) String cohorte) {
		
		List<IndexDocIdf> indexDocs =  (List<IndexDocIdf>) indexDocIdfRepo.findByCertaintyAndContextAndCohorte("1", "patient_text",cohorte);
		long nbConceptTot = indexDocRepo.countByCertaintyAndContextAndCohorte("1", "patient_text",cohorte);
		
//				new IndexDocDao(psClient).getAllIndexDoc();
		
		Set <String> uris =indexDocs.stream().map(c -> c.getCode()).distinct().collect(Collectors.toSet());
		HashMap<String, String> labelMap = new HashMap<String, String>();
		if(uris.size()>2000) {
			
			AtomicInteger idx = new AtomicInteger(0);
			AtomicInteger idx2 = new AtomicInteger(0);
			AtomicInteger totalSize = new AtomicInteger(uris.size());
			Set<String> urisTemps = new HashSet<String>();
			uris.forEach(u -> {
				int j = idx.incrementAndGet();
				urisTemps.add(u);
				if( j%2000 == 0 || j == totalSize.get()) {
					log.info("Exectute batch " + idx2.incrementAndGet());
					labelMap.putAll(labelMapper.getLabelForUris(urisTemps.stream()));
					urisTemps.clear();
				}
					
			});
		}else {
			labelMap.putAll(labelMapper.getLabelForUris(uris.stream()));
		}
		
		Map<String, Set<Object>> jqclouds = new HashMap<>();
		
		jqclouds.put("tf", indexDocs.stream()
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
		.collect(Collectors.toSet()));
		
		Map<String,ThesaurusEnrsem> thesaurus= indexDocs.stream().map(t -> new ThesaurusEnrsem(t.getCode(), t.getFrequency(), t.getLabel(),(float)t.getIdf()))
		.distinct()
		.collect(Collectors.toMap(ThesaurusEnrsem::getCode, t -> t));
		
		
		jqclouds.put("tfidf", indexDocs.stream()
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
		.collect(Collectors.toSet()));
		
		Map<String, Long> idf = indexDocs.stream()
		.collect(Collectors.groupingBy(IndexDocIdf::getCode,Collectors.counting()));

		Map<String, Long> tf = indexDocs.stream()
				.filter(c -> c.getCertainty().equals("1") & c.getContext().equals("patient_text"))
				.map(c -> new IndexDoc(c.getPatientNum(), "", "", "", c.getCode(), "", "",""))
				.distinct()
				.collect(Collectors.groupingBy(IndexDoc::getCode,Collectors.counting()));
		
		jqclouds.put("tab", thesaurus.entrySet().stream()
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
//						(float)((float)(idf.get(t.getValue().getCode())/nbConceptTot))
//							*t.getValue().getIdf()))
				)
			);
			}})
		.collect(Collectors.toSet())
		);
		
		
		return jqclouds;
	}
}
