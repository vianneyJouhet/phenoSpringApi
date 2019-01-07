package org.erias.phenoApi.service;

import java.util.List;

import org.erias.phenoApi.model.ThesaurusFrequency;

public interface ThesaurusEnrsemService {

	List<ThesaurusFrequency> loadThesaurusFromIndexDoc();

}