package org.erias.phenoApi.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JQCloudService {

	Map<String, Set<Object>> getJQCloud(String cohorte);

	Map<String, Set<Object>> getJQCloudAggByIcForGraph(String cohorte, Double icSanchez, String graph);

	Map<String, Set<Object>> getAllJQCloud();

	Map<String, Set<Object>> getJQCloudByCodes(List<String> uris);

	Map<String, Set<Object>> getJQCloudAggByUrisAndIcForGraph(Set<String> uris, Double icSanchez, String graph);


	Map<String, Set<Object>> getJQCloudByCodesAggByHoom(List<String> uris);

}