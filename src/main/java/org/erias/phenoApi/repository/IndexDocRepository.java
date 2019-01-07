/**
 * 
 */
package org.erias.phenoApi.repository;

import java.util.List;

import org.erias.phenoApi.model.IndexDoc;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author vianney
 *
 */
public interface IndexDocRepository extends CrudRepository<IndexDoc, Long>,IndexDocLoader {
	
	List<IndexDoc> findByCertaintyAndContextAndCohorte(String certainty,String context,String cohorte);
	
	@Query("SELECT DISTINCT cohorte FROM IndexDoc")
	List<String> findDistinctCohorte();
	
	Long countByCohorte(String cohorte);
	
	Long countByCertaintyAndContextAndCohorte(String certainty,String context,String cohorte);
	
	@Query("SELECT count(distinct patientnum) from IndexDoc")
	Long countDistinctPatientNum();
	
}
