package org.erias.phenoApi.repository.rdf4j;

import java.util.Map;
import java.util.Set;

import org.erias.phenoApi.model.HoomAssociation;

public interface HoomRepository {

	Set<HoomAssociation> findAllHoomAssociationsByUris(Set<String> uris);

	Set<HoomAssociation> findAllHoomAssociationsForSubjectInferred(Set<String> uris);

	Set<HoomAssociation> findAllHoomAssociationsForSubjectInferredObject(Set<String> uris);

	Map<String, String> buildHoomStatusForSubject(Set<String> uris);

}