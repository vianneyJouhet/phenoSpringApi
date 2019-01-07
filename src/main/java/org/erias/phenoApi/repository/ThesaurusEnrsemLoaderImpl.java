package org.erias.phenoApi.repository;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.springframework.transaction.annotation.Transactional;

public class ThesaurusEnrsemLoaderImpl implements ThesaurusEnrsemLoader {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public void batchLoad(int batchSize, Set<ThesaurusEnrsem> thesaurusEnrsems) {
		// TODO Auto-generated method stub

		try {
			thesaurusEnrsems.forEach(i -> {
				entityManager.persist(i);
			});
		} finally {
			entityManager.close();
		}

	}

	

}
