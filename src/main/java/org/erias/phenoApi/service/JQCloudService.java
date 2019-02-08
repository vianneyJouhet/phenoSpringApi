package org.erias.phenoApi.service;

import java.util.Map;
import java.util.Set;

public interface JQCloudService {

	Map<String, Set<Object>> getJQCloud(String cohorte);

	Map<String, Set<Object>> getJQCloudAggByIcForGraph(String cohorte, Double icSanchez, String graph);

}