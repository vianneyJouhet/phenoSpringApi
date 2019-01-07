package org.erias.phenoApi.repository;

import java.util.Set;

import org.erias.phenoApi.model.IndexDoc;

public interface IndexDocLoader {

		public void batchLoad(int batchSize, Set<IndexDoc> indexDocs);
}
