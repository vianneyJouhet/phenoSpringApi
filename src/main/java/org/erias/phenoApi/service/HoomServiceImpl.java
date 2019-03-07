package org.erias.phenoApi.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.HoomAssociation;
import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.IndexDocIdf;
import org.erias.phenoApi.repository.IndexDocIdfRepository;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.erias.phenoApi.repository.rdf4j.EntityRepository;
import org.erias.phenoApi.repository.rdf4j.HoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoomServiceImpl implements HoomService {

	protected static final Logger log = LogManager.getLogger(HoomServiceImpl.class);
	
	@Autowired
	HoomRepository hoomRepository;
	
	@Autowired
	EntityRepository entityRepository;
	
	@Autowired 
	IndexDocIdfService idfService;
	
	@Autowired 
	IndexDocRepository indexDocRepo;
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.HoomService#aggByHoom(java.util.Set)
	 */
	@Override
	public List<IndexDocIdf> aggByHoom(Set<String> uris, Set<String> patientNums){
		Set<HoomAssociation> hoomAssociations = hoomRepository.findAllHoomAssociationsByUris(uris);
		Set<String> uriObject =hoomAssociations.stream().map(HoomAssociation::getUriObject).collect(Collectors.toSet());
		EntityHierarchie entityHierarchie = entityRepository.findSubClassesBatch(uriObject)
				.setDeepestAncestors();
		return idfService.findByPatientNumAndCodesInHierarchie(entityHierarchie, patientNums);
	}
	
	
	
}
