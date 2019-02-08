package org.erias.phenoApi.repository.rdf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.query.BindingSet;
import org.erias.phenoApi.model.ICFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ICFeatureRepositoryImpl extends RDF4JRepositoryImpl implements ICFeatureRepository {

	@Value("${sparql.protocol}") 
	private static String protocol;
	@Value("${sparql.port}") 
	private static int port;
	@Value("${sparql.domain}") 
	private static String url;
	@Value("${sparql.endpoint}")
	private static String namespace;
	
	
	/**
	 * @param protocol
	 * @param url
	 * @param port
	 * @param namespace
	 */
	public ICFeatureRepositoryImpl(@Value("${sparql.protocol}") String sparqlProtocol,
			@Value("${sparql.port}") int sparqlPort, @Value("${sparql.domain}") String sparqlUrl,
			@Value("${sparql.endpoint}") String sparqlNamespace) {
		super(sparqlProtocol, sparqlUrl, sparqlPort, sparqlNamespace);
		// TODO Auto-generated constructor stub
	}
	
//
//	/* (non-Javadoc)
//	 * @see org.erias.phenoApi.repository.rdf4j.ICFeatureRepository#getALLICFeaturesByGraph(java.lang.String)
//	 */
//	@Override
//	public HashMap<String, ICFeature> getALLICFeaturesByGraph(String urigraph) {
//		String query = "\n"
//				+ "select  ?concept (count(distinct ?leaf) as ?leaves) (count(distinct ?subsumer)+1 as ?subsumers)\n" + 
//				"{\n" + 
//				"  {\n" + 
//				"  VALUES ?g {"+toUri(urigraph)+"}\n" + 
//				"  GRAPH ?g {\n" + 
//				"  	?leaf rdfs:subClassOf+ ?concept.\n" + 
//				"    OPTIONAL{?concept rdfs:subClassOf+ ?subsumer.}\n" + 
//				"    FILTER NOT EXISTS { ?a rdfs:subClassOf ?leaf}\n" + 
//				"  }\n" + 
//				"} UNION {\n" + 
//				"  VALUES ?g {"+toUri(urigraph)+"}\n" +
//				"	GRAPH ?g {\n" + 
//				"    ?concept rdfs:subClassOf+ ?subsumer.\n" + 
//				"    FILTER NOT EXISTS { ?leaf rdfs:subClassOf ?concept}\n" + 
//				"  }      \n" + 
//				"}\n" + 
//				"}\n" + 
//				"group by ?concept" ;
//		
//		
//		List<BindingSet> res = execSelectQuery(query); 
//		
//		HashMap<String,ICFeature> iCFeatures = new HashMap<String,ICFeature>();
//		
//		res.stream()
//		.forEach(re -> {
//			String uri = re.getValue("concept").stringValue();
//			Long leaves = Long.parseLong(re.getValue("leaves").stringValue());
//			Long subsumers = Long.parseLong(re.getValue("subsumers").stringValue());
//			String graph = re.getValue("g").stringValue();
//			ICFeature icFeature = new ICFeature(uri, leaves, subsumers,graph);
//			iCFeatures.put(uri, icFeature);
//		});
//		
//		return iCFeatures;
//	}
	
	@Override
	public Set<ICFeature> getALLICFeatureByGraph(String urigraph) {
		String query = "\n"
				+ "select  ?g ?concept (count(distinct ?leaf) as ?leaves) (count(distinct ?subsumer)+1 as ?subsumers)\n" + 
				"{\n" + 
				"  {\n" + 
				"  VALUES ?g {"+toUri(urigraph)+"}\n" + 
				"  GRAPH ?g {\n" + 
				"  	?leaf rdfs:subClassOf+ ?concept.\n" + 
				"    OPTIONAL{?concept rdfs:subClassOf+ ?subsumer.}\n" + 
				"    FILTER NOT EXISTS { ?a rdfs:subClassOf ?leaf}\n" + 
				"	 FILTER (!isBlank(?leaf)) \n "+
				"	 FILTER (!isBlank(?concept)) \n "+
				"  }\n" + 
				"} UNION {\n" + 
				"  VALUES ?g {"+toUri(urigraph)+"}\n" +
				"	GRAPH ?g {\n" + 
				"    ?concept rdfs:subClassOf+ ?subsumer.\n" + 
				"    FILTER NOT EXISTS { ?leaf rdfs:subClassOf ?concept}\n" + 
//				"	 FILTER (!isBlank(?leaf)) \n "+
				"	 FILTER (!isBlank(?subsumer)) \n "+
				"  }      \n" + 
				"}\n" + 
				"}\n" + 
				"group by ?concept ?g" ;
		
		
		List<BindingSet> res = execSelectQuery(query); 
		
		Set<ICFeature> iCFeatures = new HashSet<ICFeature>();
		
		res.stream()
		.forEach(re -> {
			String uri = re.getValue("concept").stringValue();
			Long leaves = Long.parseLong(re.getValue("leaves").stringValue());
			Long subsumers = Long.parseLong(re.getValue("subsumers").stringValue());
			String graph = re.getValue("g").stringValue();
			ICFeature icFeature = new ICFeature(uri, leaves, subsumers,graph);
			iCFeatures.add(icFeature);
		});
		
		return iCFeatures;
	}
	

}
