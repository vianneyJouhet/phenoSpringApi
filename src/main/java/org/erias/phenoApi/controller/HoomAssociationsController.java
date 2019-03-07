package org.erias.phenoApi.controller;

import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.HoomAssociation;
import org.erias.phenoApi.repository.rdf4j.HoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HoomAssociationsController {
	protected static final Logger log = LogManager.getLogger(HoomAssociationsController.class);
	
	@Autowired
	HoomRepository hoomRepository;
	
	@RequestMapping(value = "/hoom-associatations/uri",method = RequestMethod.POST )
	public Set<HoomAssociation>  getHoomAssociationByUris(@RequestBody(required=true) Set<String> uris) {
		
				
		return hoomRepository.findAllHoomAssociationsByUris(uris);
	}
	
	
	@RequestMapping(value = "/hoom-associatations/subject/uri",method = RequestMethod.POST )
	public Set<HoomAssociation>  getHoomAssociationBySubjectUrisInferredObject(@RequestBody(required=true) Set<String> uris) {
						
		return hoomRepository.findAllHoomAssociationsForSubjectInferredObject(uris);
	}
	
	@RequestMapping(value = "/hoom-status/subject/uri",method = RequestMethod.POST )
	public Map<String, String>  getHoomStatusForSubjectUris(@RequestBody(required=true) Set<String> uris) {
						
		return hoomRepository.buildHoomStatusForSubject(uris);
	}
	
	@RequestMapping(value = "/hoom-associatations/subject/inferred/uri",method = RequestMethod.POST )
	public Set<HoomAssociation>  getHoomAssociationBySubjectUrisInferred(@RequestBody(required=true) Set<String> uris) {
						
		return hoomRepository.findAllHoomAssociationsForSubjectInferred(uris);
	}
}
