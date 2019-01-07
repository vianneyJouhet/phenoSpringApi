package org.erias.phenoApi.repository;

import java.util.Set;

import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.ThesaurusEnrsem;

public interface ThesaurusEnrsemLoader {

		public void batchLoad(int batchSize, Set<ThesaurusEnrsem> indexDocs);
}
