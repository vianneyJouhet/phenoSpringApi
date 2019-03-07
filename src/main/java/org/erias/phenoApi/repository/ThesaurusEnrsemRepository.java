package org.erias.phenoApi.repository;

import java.util.List;
import java.util.Set;

import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ThesaurusEnrsemRepository extends CrudRepository<ThesaurusEnrsem, String>, ThesaurusEnrsemLoader{
	@Query("SELECT new org.erias.phenoApi.model.ThesaurusEnrsem ("
			+ "code as code,"
			+ " COUNT(DISTINCT patientnum) as frequency) "
			+ "FROM IndexDoc "
			+ "WHERE certainty='1' AND context = 'patient_text' "
			+ "GROUP BY code")
	List<ThesaurusEnrsem> findCodeCount();
	
	List<ThesaurusEnrsem> findByIcSanchezLessThan(Double icSanchez);
	
	List<ThesaurusEnrsem> findByinferedMetricsAndFrequencyGreaterThanEqual(boolean inferred,long frequency);
	
	List<ThesaurusEnrsem> findByCodeIn(Set<String> code);
	
	List<ThesaurusEnrsem> findByInferedMetricsAndGraphAndCodeIn(boolean inferedMetrics,String graph,Set<String> code);
	
	List<ThesaurusEnrsem> findByInferedMetricsAndCodeIn(boolean inferedMetrics,Set<String> code);
}
