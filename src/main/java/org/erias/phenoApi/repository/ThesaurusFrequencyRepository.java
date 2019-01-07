package org.erias.phenoApi.repository;

import java.util.List;

import org.erias.phenoApi.model.ThesaurusFrequency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ThesaurusFrequencyRepository extends CrudRepository<ThesaurusFrequency, String>{
	
	@Query("SELECT new org.erias.phenoApi.model.ThesaurusFrequency ("
			+ "code as code,"
			+ " COUNT(DISTINCT patientnum) as frequency) "
			+ "FROM IndexDoc "
			+ "WHERE certainty='1' AND context = 'patient_text' "
			+ "GROUP BY code")
	List<ThesaurusFrequency> findCodeCount();
}
