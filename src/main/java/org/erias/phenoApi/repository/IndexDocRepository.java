/**
 * 
 */
package org.erias.phenoApi.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.erias.phenoApi.model.IndexDoc;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author vianney
 *
 */
public interface IndexDocRepository extends CrudRepository<IndexDoc, Long>,IndexDocLoader {
	
	List<IndexDoc> findByCertaintyAndContextAndCohorte(String certainty,String context,String cohorte);
	
	List<IndexDoc> findByCertaintyAndContextAndCohorteAndCodeIn(String certainty,String context,String cohorte,Set<String> code);
	
	List<IndexDoc> findByCertaintyAndContextAndCodeIn(String certainty,String context,Set<String> code);
	
	@Query("SELECT DISTINCT cohorte FROM IndexDoc")
	List<String> findDistinctCohorte();
	
	@Query("SELECT  CONCAT(cohorte,CONCAT('-',count(distinct patientnum))) as ret FROM  IndexDoc group by cohorte")
	List<String> findDistinctCohorteAndCount();
	
	Long countByCohorte(String cohorte);
	
	Long countByCertaintyAndContextAndCohorte(String certainty,String context,String cohorte);
	
	@Query("SELECT count(distinct patientnum) from IndexDoc")
	Long countDistinctPatientNum();

	long countByCertaintyAndContext(String string, String string2);

	long countByCertaintyAndContextAndPatientNumIn(String string, String string2, Set<String> patientNums);

	
}
