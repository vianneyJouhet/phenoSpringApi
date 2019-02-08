package org.erias.phenoApi.repository.rdf4j;

import java.util.Set;

import org.erias.phenoApi.model.ThesaurusEnrsem;

public interface GraphRepository {

	Set<String> findAllGraph();

	Set<String> findAllThesaurusGraph();
	
	Set<String> findAllStructuralGraph();

}