package org.erias.phenoApi.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.ThesaurusEnrsem;


public interface ThesaurusEnrsemService {

	List<ThesaurusEnrsem> loadThesaurusFromIndexDoc();

	void loadThesaurusEnrSemFromIndexDoc(String protocol, int port, String url, String namespace);

	void updateThesaurusEnrSem();



	Set<ThesaurusEnrsem> findEnrsemByCodesInHierarchie(Set<String> codes, String Graph);


	Set<ThesaurusEnrsem> findEnrsemByIcInHierarchieInGraph(Double icSanchez, String graph);

	Map<String, ThesaurusEnrsem> findEnrsemByCodesInHierarchie(EntityHierarchie entityHierarchie);

	Map<String, ThesaurusEnrsem> findEnrsemInferredByCodesIn(Set<String> codes, String graph);
	

}