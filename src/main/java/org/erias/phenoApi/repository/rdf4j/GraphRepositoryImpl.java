package org.erias.phenoApi.repository.rdf4j;

import java.util.Set;
import java.util.stream.Collectors;

import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class GraphRepositoryImpl extends RDF4JRepositoryImpl implements GraphRepository {

	/**
	 * @param protocol
	 * @param url
	 * @param port
	 * @param namespace
	 */
	public GraphRepositoryImpl(@Value("${sparql.protocol}") String sparqlProtocol,
			@Value("${sparql.port}") int sparqlPort, @Value("${sparql.domain}") String sparqlUrl,
			@Value("${sparql.endpoint}") String sparqlNamespace) {
		super(sparqlProtocol, sparqlUrl, sparqlPort, sparqlNamespace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<String> findAllGraph() {
		String query = "\n" + "select  distinct ?graph \n" + "{\n" + "		GRAPH ?graph {\n" + "			?a ?b ?c \n"
				+ "		}\n" + "}\n";

		return execSelectQuery(query).stream().map(g -> g.getValue("graph").stringValue()).collect(Collectors.toSet());

	}

	@Override
	public Set<String> findAllThesaurusGraph() {
		String query = "\n" 
			+ "select  distinct ?graph \n" 
			+ "	{\n"
			+ "		?graph a <http://erias.org/thesaurus> \n"
			+ "	}\n";

		return execSelectQuery(query).stream().map(g -> g.getValue("graph").stringValue()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> findAllStructuralGraph() {
		String query = "\n" 
				+ "select  distinct ?graph \n" 
				+ "	{\n"
				+ "		?graph a <http://erias.org/structural> \n" 
				+ "	}\n";

		return execSelectQuery(query).stream().map(g -> g.getValue("graph").stringValue()).collect(Collectors.toSet());
	}
}
