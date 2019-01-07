package org.erias.phenoApi.repository;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.erias.phenoApi.model.IndexDoc;
import org.springframework.transaction.annotation.Transactional;

public class IndexDocLoaderImpl implements IndexDocLoader {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public void batchLoad(int batchSize, Set<IndexDoc> indexDocs) {
		// TODO Auto-generated method stub

		try {
			indexDocs.forEach(i -> {
				entityManager.persist(i);
			});
		} finally {
			entityManager.close();
		}

	}

}
