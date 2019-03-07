package org.erias.phenoApi.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.CoocurenceMatrix;
import org.erias.phenoApi.model.CoocurenceMetric;
import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.IndexDocIdf;
import org.erias.phenoApi.model.PostGraphIc;
import org.erias.phenoApi.repository.IndexDocIdfRepository;
import org.erias.phenoApi.repository.rdf4j.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoocurenceServiceImpl implements CoocurenceService {
	
	protected static final Logger log = LogManager.getLogger(CoocurenceServiceImpl.class);
	
		
	@Autowired
	private IndexDocIdfRepository indexDocIdfRepository;
	
	@Autowired 
	private EntityRepository oWLClassRepository;
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.CoocurenceService#getCoocurenceByCohorte(java.lang.String)
	 */
	@Override
	public CoocurenceMatrix getCoocurenceByCohorte(String cohorte,Set<String> codes,String graph){
		
		EntityHierarchie entityHierarchie = oWLClassRepository.findSubClassesBatch(codes);
		entityHierarchie.setDeepestAncestors();
		
		Set<String> codeList = entityHierarchie.getAllChilds();
		
		CoocurenceMatrix coocurence = new CoocurenceMatrix();
		
		coocurence.addAllCoocurencesFromIndexDocs(
				indexDocIdfRepository.findByCertaintyAndContextAndCohorteAndCodeIn("1", "patient_text",cohorte,codeList),
				entityHierarchie,
				graph
				);

		return coocurence;
	}
	
	@Override
	public CoocurenceMatrix getCoocurenceByCohorteAndICInGraphs(String cohorte,Set<PostGraphIc> graphIcs){
		
		HashMap<String, EntityHierarchie >entityHierarchies = oWLClassRepository.findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(graphIcs);
		
		entityHierarchies.entrySet()
		.parallelStream().forEach( e -> {
			entityHierarchies.put(e.getKey(), e.getValue().setDeepestAncestors());
		});
		
		CoocurenceMatrix coocurence = new CoocurenceMatrix();
		entityHierarchies.keySet().forEach(g1 -> {
			entityHierarchies.keySet().forEach(g2 -> {
				if(! g1.equals(g2)) {
					coocurence.addAllCoocurencesFromIndexDocs(
							indexDocIdfRepository.findByCertaintyAndContextAndCohorteAndCodeInAndInferedMetrics(
									"1", "patient_text",
									cohorte,
									entityHierarchies.get(g1).getAllChilds(),false),
							entityHierarchies.get(g1),
							g1,
							indexDocIdfRepository.findByCertaintyAndContextAndCohorteAndCodeInAndInferedMetrics(
									"1", "patient_text",
									cohorte,
									entityHierarchies.get(g2).getAllChilds(),false),
							entityHierarchies.get(g2),
							g2);
				}
			});
		});
		return coocurence;
	}
	
	@Override
	public CoocurenceMatrix getCoocurenceByCohorteAndICInGraphsAndCodes(String cohorte,Set<PostGraphIc> graphIcs,Set<String> uris){
		
		HashMap<String, EntityHierarchie >entityHierarchies = oWLClassRepository.findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(graphIcs);
		
		EntityHierarchie CodeHierarchies = oWLClassRepository.findSubClasses(uris).setDeepestAncestors();
		
		entityHierarchies.entrySet()
		.parallelStream().forEach( e -> {
			entityHierarchies.put(e.getKey(), e.getValue().setDeepestAncestors());
		});
		
		CoocurenceMatrix coocurence = new CoocurenceMatrix();
		if(!cohorte.equals("None")) {
			entityHierarchies.keySet().forEach(g1 -> {
					coocurence.addAllCoocurencesFromIndexDocs(
						indexDocIdfRepository.findByCertaintyAndContextAndCohorteAndCodeInAndInferedMetrics(
								"1", "patient_text",
								cohorte,
								entityHierarchies.get(g1).getAllChilds(),false),
						entityHierarchies.get(g1),
						g1,
						indexDocIdfRepository.findByCertaintyAndContextAndCohorteAndCodeInAndInferedMetrics(
								"1", "patient_text",
								cohorte,
								CodeHierarchies.getAllChilds(),false),
						CodeHierarchies,
						"abc");
			});
		}else {
			entityHierarchies.keySet().forEach(g1 -> {
				coocurence.addAllCoocurencesFromIndexDocs(
					indexDocIdfRepository.findByCertaintyAndContextAndCodeInAndInferedMetrics(
							"1", "patient_text",
							entityHierarchies.get(g1).getAllChilds(),false),
					entityHierarchies.get(g1),
					g1,
					indexDocIdfRepository.findByCertaintyAndContextAndCodeInAndInferedMetrics(
							"1", "patient_text",
							CodeHierarchies.getAllChilds(),false),
					CodeHierarchies,
					"abc");
			});
			log.info("All cohorte");
		}
		return coocurence;
	}
	
	@Override
	public CoocurenceMatrix getCoocurenceByCohorteAndICInGraph(String cohorte,Double icSanchez,String graph){
		
		EntityHierarchie entityHierarchie = oWLClassRepository.findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(graph, icSanchez);
		entityHierarchie.setDeepestAncestors();
		
		Set<String> codeList = entityHierarchie.getAllChilds();
		
		CoocurenceMatrix coocurence = new CoocurenceMatrix();
		
		coocurence.addAllCoocurencesFromIndexDocs(
				indexDocIdfRepository.findByCertaintyAndContextAndCohorteAndCodeInAndInferedMetrics("1", "patient_text",cohorte,codeList,false),
				entityHierarchie,
				graph
				);

		return coocurence;
	}
	
	@Override
	public Set<CoocurenceMetric> getCoocurenceMericsByCohorte(String cohorte,Set<String> codes){
		return getCoocurenceByCohorte(cohorte, codes,null).computeMetrics();
	}

	@Override
	public CoocurenceMatrix getCoocurenceByCohorte(String cohorte, Set<String> codes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

