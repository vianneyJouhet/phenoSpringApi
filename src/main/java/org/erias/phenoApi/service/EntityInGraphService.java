package org.erias.phenoApi.service;

import java.util.Set;

import org.erias.phenoApi.model.EntityInGraph;
import org.erias.phenoApi.model.InformationContent;

public interface EntityInGraphService {

	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.EntityInGraph#getAllEntityInGraph(java.lang.String)
	 */
	Set<EntityInGraph> getAllEntityInGraph(String graph);

	Set<EntityInGraph> getAllEntityInGraph(Set<String> graph);

	void updateIC(InformationContent ic);

	Set<EntityInGraph> getEntityByIcSanchezLessThanInGraph(String graph, Double icSanchez);

	void updateIC(Set<InformationContent> ic);

	void computeGraphMetrics(String graph);

	void updateGraphMetrics();

}