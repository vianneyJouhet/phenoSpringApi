package org.erias.phenoApi.service;

import java.util.List;
import java.util.Set;

import org.erias.phenoApi.model.IndexDocIdf;

public interface HoomService {

	

	List<IndexDocIdf> aggByHoom(Set<String> uris, Set<String> patientNums);

}