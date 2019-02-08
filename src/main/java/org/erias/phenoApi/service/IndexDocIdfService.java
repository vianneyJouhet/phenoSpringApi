package org.erias.phenoApi.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.IndexDocIdf;
import org.erias.phenoApi.model.ThesaurusEnrsem;

public interface IndexDocIdfService {

	List<IndexDocIdf> findByIcInHierarchieInGraph(String cohorte, Double icSanchez, String graph);

	List<IndexDocIdf> findByCodesInHierarchie(EntityHierarchie entityHierarchie,
			Map<String, ThesaurusEnrsem> thesaurusEnrsems, String cohorte);

}