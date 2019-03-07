package org.erias.phenoApi.repository.rdf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.EntityInGraph;
import org.erias.phenoApi.model.InformationContent;
import org.erias.phenoApi.model.PostGraphIc;

public interface EntityRepository {

	EntityHierarchie findSubClasses(Set<String> uris);

	Set<EntityInGraph> findAllEntityInGraph(String graph);

	Set<EntityInGraph> findAllEntityInGraph(Set<String> graph);

	void updateMetricsForEntity(Set<InformationContent> ic);
	
	void updateMetricsForEntity(InformationContent ic);
	
	EntityHierarchie findSubClassesBatch(Set<String> uris);


	Set<EntityInGraph> findAllEntityByIcSanchezLowerOrEqualThanInGraph(String graph, Double icSanchez);

	EntityHierarchie findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(String graph, Double icSanchez);

	HashMap<String, EntityHierarchie> findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(Set<String> graphs, Double icSanchez);

	HashMap<String, EntityHierarchie> findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(
			Set<PostGraphIc> graphIcs);

	Set<EntityInGraph> findAllEntity();

	Map<String, Set<String>> findAllUrisInThesaurusGraph();

	EntityHierarchie findSuperClasses(Set<String> uris);

}