package org.erias.phenoApi.repository.rdf4j;

import java.util.HashMap;
import java.util.Set;

import org.erias.phenoApi.model.ICFeature;

public interface ICFeatureRepository {

//	HashMap<String, ICFeature> getALLICFeaturesByGraph(String urigraph);

	Set<ICFeature> getALLICFeatureByGraph(String urigraph);

}