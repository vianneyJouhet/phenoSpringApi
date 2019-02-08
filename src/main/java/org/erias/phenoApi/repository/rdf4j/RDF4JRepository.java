package org.erias.phenoApi.repository.rdf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.rdf4j.query.BindingSet;

public interface RDF4JRepository {


	List<BindingSet> execSelectQuery(String query);
	String toUriList(Set<String> uris);
	String toUriList(Stream<String> uris);
	String toUri(String uri);
	void remove(String subject, String predicate, String object, String graph);
	void add(String subject, String predicate, String object, String graph);
//	void addLiteral(String subject, String predicate, Double object, String graph);
	void removeLiteral(String subject, String predicate, String object, String graph);
	void addLiteral(String subject, String predicate, String object, String graph);
}