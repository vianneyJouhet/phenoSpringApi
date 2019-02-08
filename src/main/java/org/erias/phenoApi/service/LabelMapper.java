package org.erias.phenoApi.service;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface LabelMapper {

	HashMap<String, String> getLabelForUris(Stream<String> uris);

	HashMap<String, String> getPrefLabelForUrisBatch(Set<String> uris);

	HashMap<String, String> getPrefLabelForUris(Stream<String> uris);

	/**
	 * @return the labelMap
	 */
	HashMap<String, String> getLabelMap();

	/**
	 * @param labelMap the labelMap to set
	 */
	void setLabelMap(HashMap<String, String> labelMap);

}