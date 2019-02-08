package org.erias.phenoApi.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.query.BindingSet;
import org.erias.phenoApi.repository.rdf4j.RDF4JRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.chu.bordeaux.RDF4JClient.services.RDF4JClient;

public class LabelMapperImpl extends RDF4JRepositoryImpl implements LabelMapper {

	protected static final Logger log = LogManager.getLogger(LabelMapperImpl.class);

	
	private HashMap<String,String> labelMap;
	
	
	public LabelMapperImpl(String protocol, String url, int port, String namespace,Stream<String> uris) {
		super(protocol, url, port, namespace);
		this.setLabelMap(getLabelForUris(uris));
		
	}
	
	
	
	public LabelMapperImpl(String protocol, String url, int port, String namespace) {
		super(protocol, url, port, namespace);
	}
	

	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.LabelMapperI#getLabelForUris(java.util.stream.Stream)
	 */
	@Override
	public HashMap<String,String> getLabelForUris(Stream<String> uris) {
		
		String values = toUriList(uris);
		
		
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
		
		

		List<BindingSet> res = execSelectQuery(query);
		HashMap<String,String> mapLabels = new HashMap<String,String>();
		res.stream()
		.forEach(re -> {
			mapLabels.put(re.getValue("uri").stringValue(),re.getValue("label").stringValue());
		});
		return mapLabels;

	}

	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.LabelMapperI#getPrefLabelForUrisBatch(java.util.Set)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.LabelMapperI#getPrefLabelForUris(java.util.stream.Stream)
	 */
	@Override
	public HashMap<String,String> getPrefLabelForUris(Stream<String> uris) {
		
		String values = uris
		.map(u -> "<"  + u + "> ")
		.reduce("", String::concat);
		
		
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
		
		List<BindingSet> res = execSelectQuery(query);
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
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.LabelMapperI#getLabelMap()
	 */
	@Override
	public HashMap<String,String> getLabelMap() {
		return labelMap;
	}

	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.LabelMapperI#setLabelMap(java.util.HashMap)
	 */
	@Override
	public void setLabelMap(HashMap<String,String> labelMap) {
		this.labelMap = labelMap;
	}
	
}
