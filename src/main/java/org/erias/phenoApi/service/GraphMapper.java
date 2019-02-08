package org.erias.phenoApi.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.query.BindingSet;
import org.erias.phenoApi.repository.rdf4j.RDF4JRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;

import fr.chu.bordeaux.RDF4JClient.services.RDF4JClient;

public class GraphMapper extends RDF4JRepositoryImpl {

	protected static final Logger log = LogManager.getLogger(GraphMapper.class);

	private String sparqlEndpoint;
	private HashMap<String,Set<String>> graphMap;
	@Value("${sparql.protocol}") 
	private static String protocol;
	@Value("${sparql.port}") 
	private static int port;
	@Value("${sparql.domain}") 
	private static String url;
	@Value("${sparql.endpoint}")
	private static String namespace;
	
	public GraphMapper(String protocol, String url, int port, String namespace,Stream<String> uris) {
			super(protocol, url, port, namespace);
			this.setGraphMap(getTermInfo(uris));
		
	}
	
	public GraphMapper(Stream<String> uris) {
		super(protocol, url, port, namespace);
		this.setGraphMap(getTermInfo(uris));
	
	}
	
	public GraphMapper(String protocol, String url, int port, String namespace) {
		super(protocol, url, port, namespace);
	}
	
	public GraphMapper() {
		super(protocol, url, port, namespace);
	}
	
	public HashMap<String,Set<String>> getTermInfo(Stream<String> values) {
		
		String v = toUriList(values);
		
		log.info(v);
		String query = "" + "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + "\n"
				+ "select  distinct ?graph ?uri \n" 
				+ "{\n"
				+ "	VALUES ?uri { " + v +" } \n"
				+ "	GRAPH ?graph { \n"
				+ "		?uri rdfs:label|skos:prefLabel|skos:altLabel ?label \n"
				+ "		FILTER (lang(?label)='fr') \n"
				+ "	} \n" 
				+ "} \n";

		List<BindingSet> res = execSelectQuery(query);
		HashMap<String,Set<String>> terminfo=new HashMap<String,Set<String>>();
		res.stream()
				.forEach(b -> {
					String uri = b.getValue("uri").stringValue();
					String graph = b.getValue("graph").stringValue();
					Set<String> uris = new HashSet<String>();
					if (terminfo.containsKey(graph)) {
						uris=terminfo.get(graph);
					}
					uris.add(uri);
					terminfo.put(graph, uris);
				});
		return terminfo; 
	}

	/**
	 * @return the graphMap
	 */
	public HashMap<String,Set<String>> getGraphMap() {
		return graphMap;
	}

	/**
	 * @param graphMap the graphMap to set
	 */
	public void setGraphMap(HashMap<String,Set<String>> graphMap) {
		this.graphMap = graphMap;
	}
}
