package org.erias.phenoApi.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.query.BindingSet;

import fr.chu.bordeaux.RDF4JClient.services.RDF4JClient;

public class GraphMapper {

	protected static final Logger log = LogManager.getLogger(GraphMapper.class);

	private String sparqlEndpoint;
	private HashMap<String,Set<String>> graphMap;
	
	public GraphMapper(String protocol, String url, int port, String namespace,Stream<String> uris) {
		try {
			this.sparqlEndpoint = new URL(protocol, url, port, namespace).toExternalForm();
			this.setGraphMap(getTermInfo(uris));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GraphMapper(String protocol, String url, int port, String namespace) {
		try {
			this.sparqlEndpoint = new URL(protocol, url, port, namespace).toExternalForm();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HashMap<String,Set<String>> getTermInfo(Stream<String> values) {
		
		String v = values
				.map(u -> "<"  + u + ">")
				.reduce("", String::concat);
		
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
		
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

		List<BindingSet> res = client.execTupleQuery(query);
		log.info("Number of label entries : " + res.size());
		client.close();
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
