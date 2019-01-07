/**
 * 
 */
package org.erias.phenoApi.repository;

import java.util.List;

import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.IndexDocIdf;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author vianney
 *
 */
public interface IndexDocIdfRepository extends CrudRepository<IndexDocIdf, Long>,IndexDocLoader {
	
	List<IndexDocIdf> findByCertaintyAndContextAndCohorte(String certainty,String context,String cohorte);
	
	
}
