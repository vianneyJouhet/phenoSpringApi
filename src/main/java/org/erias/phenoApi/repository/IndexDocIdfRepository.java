/**
 * 
 */
package org.erias.phenoApi.repository;

import java.util.List;
import java.util.Set;

import org.erias.phenoApi.model.IndexDocIdf;
import org.springframework.data.repository.CrudRepository;

/**
 * @author vianney
 *
 */
public interface IndexDocIdfRepository extends CrudRepository<IndexDocIdf, Long>,IndexDocLoader {
	
	List<IndexDocIdf> findByCertaintyAndContextAndCohorte(String certainty,String context,String cohorte);
	List<IndexDocIdf> findByCertaintyAndContextAndCohorteAndInferedMetrics(String certainty,String context,String cohorte,boolean inferedMetrics);
	List<IndexDocIdf> findByCertaintyAndContextAndCohorteAndCodeIn(String certainty,String context,String cohorte,Set<String> codes);
	List<IndexDocIdf> findByCertaintyAndContextAndCohorteAndCodeInAndInferedMetrics(String certainty,String context,String cohorte,Set<String> codes,boolean inferedMetrics);
	List<IndexDocIdf> findByCertaintyAndContextAndCodeInAndInferedMetrics(String certainty,String context,Set<String> codes,boolean inferedMetrics);
	
}
