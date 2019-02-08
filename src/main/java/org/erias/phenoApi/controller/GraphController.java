package org.erias.phenoApi.controller;

import java.util.Set;

import org.erias.phenoApi.repository.rdf4j.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphController {

	@Autowired
	GraphRepository graphRepository;
	
	@RequestMapping(value = "/graph/structural",method = RequestMethod.GET )
	public Set<String> getAllStructuralGraph(){
		return graphRepository.findAllStructuralGraph();
	}
	
	@RequestMapping(value = "graph/thesaurus", method = RequestMethod.GET)
	public Set<String> getAllThesaurusGraph(){
		return graphRepository.findAllThesaurusGraph();
	}
	
	@RequestMapping(value = "graph", method = RequestMethod.GET)
	public Set<String> getAllGraph(){
		return graphRepository.findAllGraph();
	}

}
