package org.erias.phenoApi.repository.rdf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.erias.phenoApi.model.HoomAssociation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class HoomRepositoryImpl extends RDF4JRepositoryImpl implements HoomRepository{

	public HoomRepositoryImpl(@Value("${sparql.protocol}") String sparqlProtocol,
			@Value("${sparql.port}") int sparqlPort, @Value("${sparql.domain}") String sparqlUrl,
			@Value("${sparql.endpoint}") String sparqlNamespace) {
		
		super(sparqlProtocol, sparqlUrl, sparqlPort, sparqlNamespace);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.erias.phenoApi.repository.rdf4j.HoomRepository#findAllHoomAssociationsByUris(java.util.Set)
	 */
	@Override
	public Set<HoomAssociation> findAllHoomAssociationsByUris (Set<String> uris) {
		
		String query = "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n" + 
				"SELECT DISTINCT  ?association ?labelsubject ?labelobject  ?irisubject ?iriobject ?irifrequency ?labelfrequency\n" + 
				"\n" + 
				"WHERE {\n" + 
				"	VALUES ?iriquest{\n" + 
				"		" +	toUriList(uris) + "\n" + 
				"	}\n" + 
				"	\n" + 
				"	#Associations\n" + 
				"	?association owl:equivalentClass ?collection .\n" + 
				"	?collection owl:intersectionOf ?list .\n" + 
				"	?list rdf:rest*/rdf:first ?item .\n" + 
				"	?item owl:someValuesFrom ?iriquest.\n" + 
				"\n" + 
				"	?association owl:equivalentClass ?node .\n" + 
				"	?node owl:intersectionOf ?list .\n" + 
				"	\n" + 
				"	#Subject\n" + 
				"	?list rdf:rest*/rdf:first ?itemsubject .\n" + 
				"	?itemsubject owl:onProperty <http://purl.org/oban/association_has_subject>. \n" + 
				"	?itemsubject owl:someValuesFrom ?irisubject . \n" + 
				"	OPTIONAL {?irisubject rdfs:label ?labelsubject} \n" + 
				"\n" + 
				"	#object\n" + 
				"	?list rdf:rest*/rdf:first ?itemobject .\n" + 
				"	?itemobject owl:onProperty <http://purl.org/oban/association_has_object>. \n" + 
				"	?itemobject owl:someValuesFrom ?iriobject . \n" + 
				"	OPTIONAL {?iriobject skos:prefLabel ?labelobject}\n" + 
				"\n" + 
				"  	#frequency\n" + 
				"	?list rdf:rest*/rdf:first ?itemFrequency .\n" + 
				"	?itemFrequency owl:onProperty <http://www.semanticweb.org/ontology/HOOM#has_frequency> .\n" + 
				"	?itemFrequency owl:someValuesFrom ?irifrequency . \n" + 
				"	OPTIONAL {?irifrequency rdfs:label ?labelfrequency} \n" + 
				"  	\n" + 
				"    \n" + 
				"    FILTER (lang(?labelsubject)='fr')\n" + 
				"}";
	
		return ExecuteAndMapHoomAssociations(query,false);
	}
	
	@Override
	public Set<HoomAssociation> findAllHoomAssociationsForSubjectInferred (Set<String> uris) {
		
		String query = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"prefix skos: <http://www.w3.org/2004/02/skos/core#>\n" + 
				"SELECT DISTINCT  ?association   ?irisubject ?iriobject ?iriobjectinferred ?irifrequency ?labelfrequency\n" + 
				"\n" + 
				"WHERE {\n" + 
				"  	\n" + 
				"      VALUES ?irisubject{\n" + 
				"		" +	toUriList(uris) + "\n" + 
				"      }\n" + 
				"\n" + 
				"      ?irisubject rdfs:subclassOf* ?pereSubject .\n" + 
				"      #Associations\n" + 
				"      ?association owl:equivalentClass ?collection .\n" + 
				"      ?collection owl:intersectionOf ?list .\n" + 
				"\n" + 
				"\n" + 
				"      #Subject\n" + 
				"      ?list rdf:rest*/rdf:first ?itemsubject .\n" + 
				"      ?itemsubject owl:onProperty <http://purl.org/oban/association_has_subject>. \n" + 
				"      ?itemsubject owl:someValuesFrom ?pereSubject . \n" + 
				"      #OPTIONAL {?irisubject rdfs:label ?labelsubject} \n" + 
				"\n" + 
				"      #object\n" + 
				"      ?list rdf:rest*/rdf:first ?itemobject .\n" + 
				"      ?itemobject owl:onProperty <http://purl.org/oban/association_has_object>. \n" + 
				"      ?itemobject owl:someValuesFrom ?iriobject . \n" + 
				"      \n" + 
				"  	  ?iriobjectinferred rdfs:subClassOf* ?iriobject .\n" + 
				"  	  #OPTIONAL {?iriobject skos:prefLabel ?labelobject}\n" + 
				"\n" + 
				"      #frequency\n" + 
				"      ?list rdf:rest*/rdf:first ?itemFrequency .\n" + 
				"      ?itemFrequency owl:onProperty <http://www.semanticweb.org/ontology/HOOM#has_frequency> .\n" + 
				"      ?itemFrequency owl:someValuesFrom ?irifrequency . \n" + 
				"      OPTIONAL {?irifrequency rdfs:label ?labelfrequency} \n" + 
				"\n" + 
				"\n" + 
				"}";
			
		return ExecuteAndMapHoomAssociations(query,true);
	}
	
	@Override
	public Set<HoomAssociation> findAllHoomAssociationsForSubjectInferredObject (Set<String> uris) {
		return ExecuteAndMapHoomAssociations(buildQueryForSubjectInferredObject(uris),true);
	}
	
	@Override
	public Map<String, String> buildHoomStatusForSubject (Set<String> uris) {
		return buildHoomStatus(buildQueryForSubjectInferredObject(uris));
	}
	
	private String buildQueryForSubjectInferredObject (Set<String> uris) {
	 return 	"prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"prefix skos: <http://www.w3.org/2004/02/skos/core#>\n" + 
				"SELECT DISTINCT  ?association   ?irisubject ?iriobject ?iriobjectinferred ?irifrequency ?labelfrequency ?iriobjectpere\n" + 
				"\n" + 
				"WHERE {\n" + 
				"  	\n" + 
				"      VALUES ?irisubject{\n" + 
				"		" +	toUriList(uris) + "\n" + 
				"      }\n" + 
				"\n" + 
//				"      ?irisubject rdfs:subclassOf* ?pereSubject .\n" + 
				"      #Associations\n" + 
				"      ?association owl:equivalentClass ?collection .\n" + 
				"      ?collection owl:intersectionOf ?list .\n" + 
				"\n" + 
				"\n" + 
				"      #Subject\n" + 
				"      ?list rdf:rest*/rdf:first ?itemsubject .\n" + 
				"      ?itemsubject owl:onProperty <http://purl.org/oban/association_has_subject>. \n" + 
				"      ?itemsubject owl:someValuesFrom ?irisubject . \n" + 
				"      #OPTIONAL {?irisubject rdfs:label ?labelsubject} \n" + 
				"\n" + 
				"      #object\n" + 
				"      ?list rdf:rest*/rdf:first ?itemobject .\n" + 
				"      ?itemobject owl:onProperty <http://purl.org/oban/association_has_object>. \n" + 
				"      ?itemobject owl:someValuesFrom ?iriobject . \n" + 
				"      \n" + 
				"  	  ?iriobjectinferred rdfs:subClassOf* ?iriobject .\n" +
				"  	  OPTIONAL {?iriobject rdfs:subClassOf+ ?iriobjectpere} .\n" +
				"  	  #OPTIONAL {?iriobject skos:prefLabel ?labelobject}\n" + 
				"\n" + 
				"      #frequency\n" + 
				"      ?list rdf:rest*/rdf:first ?itemFrequency .\n" + 
				"      ?itemFrequency owl:onProperty <http://www.semanticweb.org/ontology/HOOM#has_frequency> .\n" + 
				"      ?itemFrequency owl:someValuesFrom ?irifrequency . \n" + 
				"      OPTIONAL {?irifrequency rdfs:label ?labelfrequency} \n" + 
				"\n" + 
				"\n" + 
				"}";	
	}
	private Map<String,String> buildHoomStatus(String query){
		Map<String,String> hoomStatus = new HashMap<String,String>();
		
		execSelectQuery(query).forEach(b -> {
			hoomStatus.put(b.getValue("iriobject").stringValue(), b.getValue("labelfrequency").stringValue());
			hoomStatus.put(b.getValue("iriobjectpere").stringValue(), "Pere");
//			hoomStatus.put(b.getValue("iriobjectinferred").stringValue(), "fils");
		});
		return hoomStatus;
	}
	
	private Set<HoomAssociation> ExecuteAndMapHoomAssociations(String query,boolean hasInferredObjects){
		
		Map<String,HoomAssociation> hoomAssociations = new HashMap<String,HoomAssociation>();
		
		execSelectQuery(query).forEach(b -> {
			String key =b.getValue("association").stringValue();
			if (hoomAssociations.containsKey(key)) {
				if (hasInferredObjects) {
					HoomAssociation hoomAssociation =  hoomAssociations.get(key);
					hoomAssociation.addUriInferredObject((b.getValue("iriobjectinferred").stringValue()));
					if (b.getValue("iriobjectpere") != null) {
						hoomAssociation.addUriPereObject((b.getValue("iriobjectpere").stringValue()));
					}
					hoomAssociations.put(key,hoomAssociation);
				}
			} else {
				HoomAssociation hoomAssociation = new HoomAssociation(
					b.getValue("association").stringValue(),
					b.getValue("irisubject").stringValue(),
					b.getValue("iriobject").stringValue(),
					b.getValue("irifrequency").stringValue(),
					b.getValue("labelfrequency").stringValue()
					);
				if (hasInferredObjects) {
					hoomAssociation.addUriInferredObject((b.getValue("iriobjectinferred").stringValue()));
					if (b.getValue("iriobjectpere") != null) {
						hoomAssociation.addUriPereObject((b.getValue("iriobjectpere").stringValue()));
					}
				}
				hoomAssociations.put(key,hoomAssociation);
			}
		});;
		
		return hoomAssociations.values().stream().collect(Collectors.toSet());
	}
	
	
}
