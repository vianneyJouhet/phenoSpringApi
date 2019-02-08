package org.erias.phenoApi.service;

import java.util.Set;

import org.erias.phenoApi.model.CoocurenceMatrix;
import org.erias.phenoApi.model.CoocurenceMetric;
import org.erias.phenoApi.model.PostGraphIc;

public interface CoocurenceService {

	CoocurenceMatrix getCoocurenceByCohorte(String cohorte,Set<String> codes);

	Set<CoocurenceMetric> getCoocurenceMericsByCohorte(String cohorte, Set<String> codes);

	CoocurenceMatrix getCoocurenceByCohorteAndICInGraph(String cohorte, Double icSanchez, String graph);


	CoocurenceMatrix getCoocurenceByCohorteAndICInGraphs(String cohorte, Set<PostGraphIc> graphs);

	CoocurenceMatrix getCoocurenceByCohorte(String cohorte, Set<String> codes, String graph);

	CoocurenceMatrix getCoocurenceByCohorteAndICInGraphsAndCodes(String cohorte, Set<PostGraphIc> graphIcs,
			Set<String> uris);

//	CoocurenceMatrix getCoocurenceByCohorte(String cohorte, Set<String> codes, String graph);

//	Map<String, Set<String>> getSubClassesForUris(Set<String> codes);

}