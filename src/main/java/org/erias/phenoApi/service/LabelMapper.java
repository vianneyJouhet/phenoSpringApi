package org.erias.phenoApi.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.query.BindingSet;

import fr.chu.bordeaux.RDF4JClient.services.RDF4JClient;

public class LabelMapper {

	protected static final Logger log = LogManager.getLogger(LabelMapper.class);

	private String sparqlEndpoint;
	private HashMap<String,String> labelMap;
	
	public LabelMapper(String protocol, String url, int port, String namespace,Stream<String> uris) {
		try {
			this.sparqlEndpoint = new URL(protocol, url, port, namespace).toExternalForm();
			this.setLabelMap(getLabelForUris(uris));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LabelMapper(String protocol, String url, int port, String namespace) {
		try {
			this.sparqlEndpoint = new URL(protocol, url, port, namespace).toExternalForm();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public HashMap<String,String> getLabelForUris(Stream<String> uris) {
		
		String values = uris
		.map(u -> "<"  + u + "> ")
		.reduce("", String::concat);
		
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
		
		String query = "\n" 
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "\n"
				+ "select  distinct ?uri ?label \n" 
				+ "{\n"
				+ "		VALUES ?uri { " + values + " } \n"
				+ "		?uri rdfs:label|skos:prefLabel|skos:altLabel ?l ."
				+ "		?uri ?typelab ?l "
				+ " 	BIND ( str(?l) as ?label) \n"
				+ "		FILTER (lang(?l)='fr') \n"
				//				+ "		?uri rdfs:label ?l ."
//				+ "		?uri skos:prefLabel ?p ."
//				+ "		?uri skos:altLabel ?a ."
//				+ " 	BIND IF (\n"
//				+ "			?l != \"\",str(?l), IF ( \n"
//				+ "				?p != \"\",str(?p), str(?a)\n"
//				+ "			)"
//				+ "		)"
//				+ " 	as ?label) \n"
				+ "} \n";
		
		

		List<BindingSet> res = client.execTupleQuery(query);
		log.info("Number of label entries : " + res.size());
		client.close();
		HashMap<String,String> mapLabels = new HashMap<String,String>();
		res.stream()
		.forEach(re -> {
			mapLabels.put(re.getValue("uri").stringValue(),re.getValue("label").stringValue());
		});
		return mapLabels;

	}

	public HashMap<String,String> getPrefLabelForUrisBatch(Set<String> uris) {
		if (uris.size()>2000){
			HashMap<String, String> labels = new HashMap<String,String>();
			AtomicInteger idx = new AtomicInteger(0);
			AtomicInteger idx2 = new AtomicInteger(0);
			AtomicInteger totalSize = new AtomicInteger(uris.size());
			Set<String> urisTemps = new HashSet<String>();
			uris.forEach(u -> {
				int j = idx.incrementAndGet();
				urisTemps.add(u);
				if( j%2000 == 0 || j == totalSize.get()) {
					log.info("Exectute batch " + idx2.incrementAndGet());
					labels.putAll(getPrefLabelForUris(urisTemps.stream()));
					urisTemps.clear();
				}
					
			});
			log.info("Number of codes " + labels.size());
			return labels;
		}else {
			return getPrefLabelForUris(uris.stream());
		}
	}
	
	public HashMap<String,String> getPrefLabelForUris(Stream<String> uris) {
		
		String values = uris
		.map(u -> "<"  + u + "> ")
		.reduce("", String::concat);
		
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
		
		String query = "\n" 
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "\n"
				+ "select  distinct ?uri ?typelab ?label \n" 
				+ "{\n"
				+ "		VALUES ?uri { " + values + " } \n"
				+ "		?uri rdfs:label|skos:prefLabel|skos:altLabel ?l ."
				+ "		?uri ?typelab ?l "
				+ " 	BIND ( str(?l) as ?label) \n"
				+ "		FILTER (lang(?l)='fr') \n"
				//				+ "		?uri rdfs:label ?l ."
//				+ "		?uri skos:prefLabel ?p ."
//				+ "		?uri skos:altLabel ?a ."
//				+ " 	BIND IF (\n"
//				+ "			?l != \"\",str(?l), IF ( \n"
//				+ "				?p != \"\",str(?p), str(?a)\n"
//				+ "			)"
//				+ "		)"
//				+ " 	as ?label) \n"
				+ "} \n";
		
		List<BindingSet> res = client.execTupleQuery(query);
		log.info("Number of label entries : " + res.size());
		client.close();
		HashMap<String,String> mapLabels = new HashMap<String,String>();
		Set<String> pref = new HashSet<>();
		Set<String> lab = new HashSet<>();
		
		res.stream()
		.forEach(re -> {
			if (re.getValue("typelab").stringValue().equals("http://www.w3.org/2004/02/skos/core#prefLabel")){
				mapLabels.put(re.getValue("uri").stringValue(),re.getValue("label").stringValue());
				pref.add(re.getValue("uri").stringValue());
			}else if (re.getValue("typelab").stringValue().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#label")){
				if(!pref.contains(re.getValue("uri").stringValue())){
					mapLabels.put(re.getValue("uri").stringValue(),re.getValue("label").stringValue());
					lab.add(re.getValue("uri").stringValue());
				}
			}else {
				if(!pref.contains(re.getValue("uri").stringValue()) && !lab.contains(re.getValue("uri").stringValue())) {
					mapLabels.put(re.getValue("uri").stringValue(),re.getValue("label").stringValue());
				}
			}
		});
		log.info("Number of prefLabel entries : " + mapLabels.size());
		return mapLabels;
	}
	
	/**
	 * @return the labelMap
	 */
	public HashMap<String,String> getLabelMap() {
		return labelMap;
	}

	/**
	 * @param labelMap the labelMap to set
	 */
	public void setLabelMap(HashMap<String,String> labelMap) {
		this.labelMap = labelMap;
	}
	
}
