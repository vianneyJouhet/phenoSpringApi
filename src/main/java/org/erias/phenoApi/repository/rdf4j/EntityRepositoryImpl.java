package org.erias.phenoApi.repository.rdf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.management.StringValueExp;

import org.eclipse.rdf4j.query.BindingSet;
import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.EntityInGraph;
import org.erias.phenoApi.model.Entitylabel;
import org.erias.phenoApi.model.InformationContent;
import org.erias.phenoApi.model.PostGraphIc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class EntityRepositoryImpl extends RDF4JRepositoryImpl implements EntityRepository{

	/**
	 * @param protocol
	 * @param url
	 * @param port
	 * @param namespace
	 */
	public EntityRepositoryImpl(
			@Value("${sparql.protocol}") String sparqlProtocol,
			@Value("${sparql.port}")int sparqlPort,
			@Value("${sparql.domain}") String sparqlUrl,
			@Value("${sparql.endpoint}")String sparqlNamespace) {
		super(sparqlProtocol, sparqlUrl, sparqlPort, sparqlNamespace);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.erias.phenoApi.repository.rdf4j.OWLClassRepository#findSubClasses(java.util.Set)
	 */
	
	private String prepareQueryFindSubClasses(Set<String> uris) {
		String query = ""
				+ "select ?pere ?fils\n" + 
				"{\n" + 
				"  VALUES ?pere {"+toUriList(uris)+"}\n" + 
				"  ?fils rdfs:subClassOf* ?pere       \n" + 
				" }";
		return query;
	}
	@Override
	public EntityHierarchie findSubClasses(Set<String> uris) {
		String query =prepareQueryFindSubClasses(uris);
		
		
		EntityHierarchie entityHierarchie =new EntityHierarchie();
		execSelectQuery(query).forEach(b -> {
			entityHierarchie.addEntityHierarchie(b.getValue("pere").stringValue(), b.getValue("fils").stringValue());
		});
		
		return entityHierarchie ;
	}
	
	@Override
	public EntityHierarchie findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(String graph,Double icSanchez) {
		String query = "\n" 
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "\n"
				+ "select ?pere ?fils\n"  
				+ "{\n"
				+ "		VALUES ?g {"+ toUri(graph) + "}\n"
				+ "		GRAPH ?g {\n"
				+ "			?pere <http://erias.org/icSanchez> ?icSanchez \n"
				+ "			FILTER ( ?icSanchez <= "+ icSanchez +") \n"
				+ "  		?fils rdfs:subClassOf* ?pere    .   \n"
				+ "		}\n"
				+ "}\n";
		
		
		
		EntityHierarchie entityHierarchie =new EntityHierarchie();
		execSelectQuery(query).forEach(b -> {
			entityHierarchie.addEntityHierarchie(b.getValue("pere").stringValue(), b.getValue("fils").stringValue());
		});
		
		return entityHierarchie ;
	}
	
	@Override
	public HashMap<String, EntityHierarchie> findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(Set<String> graphs,Double icSanchez) {
		String query = "\n" 
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "\n"
				+ "select ?pere ?fils ?g\n"  
				+ "{\n"
				+ "		VALUES ?g {"+ toUriList(graphs) + "}\n"
				+ "		GRAPH ?g {\n"
				+ "			?pere <http://erias.org/icSanchez> ?icSanchez \n"
				+ "			FILTER ( ?icSanchez <= "+ icSanchez +") \n"
				+ "  		?fils rdfs:subClassOf* ?pere    .   \n"
				+ "		}\n"
				+ "}\n";
		
		
		
		HashMap<String, EntityHierarchie> entityHierarchies =new HashMap<String,EntityHierarchie>();
		execSelectQuery(query)
		.forEach(b -> {
			if (entityHierarchies.containsKey(b.getValue("g").stringValue())) {
				entityHierarchies.put(b.getValue("g").stringValue(), entityHierarchies.get(b.getValue("g").stringValue())
						.addAndGetEntityHierarchie(b.getValue("pere").stringValue(), b.getValue("fils").stringValue()));
			}else {
				EntityHierarchie entityHierarchie = new EntityHierarchie();
				entityHierarchies.put(b.getValue("g").stringValue(),entityHierarchie.addAndGetEntityHierarchie(b.getValue("pere").stringValue(), b.getValue("fils").stringValue()));
			}
		});
		
		return entityHierarchies ;
	}
	
	@Override
	public HashMap<String, EntityHierarchie> findAllEntityHierarchieByIcSanchezLowerOrEqualThanInGraph(Set<PostGraphIc> graphIcs) {
		String query = "\n" 
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "\n"
				+ "select ?pere ?fils ?g\n"  
				+ "{\n";
		
		int i=0;
		for(PostGraphIc graphIc : graphIcs) {
			i++;
			if (i>1) {
				query += "	UNION \n";
			}
			query += " { \n"
					+ "		VALUES ?g {"+ toUri(graphIc.getGraph()) + "}\n"
					+ "		GRAPH ?g {\n"
					+ "			?pere <http://erias.org/icSanchez> ?icSanchez \n"
					+ "			FILTER ( ?icSanchez <= "+ graphIc.getIc() +") \n"
					+ "  		?fils rdfs:subClassOf* ?pere    .   \n"
					+ "		}\n"	
					+"	}\n";
		};
		query +="	}\n"	;
		
		
		
		HashMap<String, EntityHierarchie> entityHierarchies =new HashMap<String,EntityHierarchie>();
		execSelectQuery(query)
		.forEach(b -> {
			if (entityHierarchies.containsKey(b.getValue("g").stringValue())) {
				entityHierarchies.put(b.getValue("g").stringValue(), entityHierarchies.get(b.getValue("g").stringValue())
						.addAndGetEntityHierarchie(b.getValue("pere").stringValue(), b.getValue("fils").stringValue()));
			}else {
				EntityHierarchie entityHierarchie = new EntityHierarchie();
				entityHierarchies.put(b.getValue("g").stringValue(),entityHierarchie.addAndGetEntityHierarchie(b.getValue("pere").stringValue(), b.getValue("fils").stringValue()));
			}
		});
		
		return entityHierarchies ;
	}
	
	@Override
	public EntityHierarchie findSubClassesBatch(Set<String> uris) {
		if (uris.size()>4000){
			EntityHierarchie entityHierarchie = new EntityHierarchie();
			AtomicInteger idx = new AtomicInteger(0);
			AtomicInteger idx2 = new AtomicInteger(0);
			AtomicInteger totalSize = new AtomicInteger(uris.size());
			Set<String> urisTemps = new HashSet<String>();
			uris.forEach(u -> {
				int j = idx.incrementAndGet();
				urisTemps.add(u);
				if( j%2000 == 0 || j == totalSize.get()) {
					log.info("Exectute batch " + idx2.incrementAndGet());
					execSelectQuery(prepareQueryFindSubClasses(urisTemps)).forEach(b -> {
						entityHierarchie.addEntityHierarchie(b.getValue("pere").stringValue(), b.getValue("fils").stringValue());	
					});
					
					urisTemps.clear();
				}
					
			});
			return entityHierarchie;
		}else {
			return findSubClasses(uris);
		}
	}
	
	@Override
	public Set<EntityInGraph> findAllEntityInGraph(String graph) {
		Set<String> g = new HashSet<String>();
		g.add(graph);
		return findAllEntityInGraph(g);
	}
	
	@Override
	public Set<EntityInGraph> findAllEntityByIcSanchezLowerOrEqualThanInGraph(String graph,Double icSanchez) {
		String query = "\n" 
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "\n"
				+ "select  distinct ?uri ?typelab ?label ?lang\n" 
				+ "{\n"
				+ "		VALUES ?g {"+ toUri(graph) + "}\n"
				+ "		GRAPH ?g {\n"
				+ "			?uri <http://erias.org/icSanchez> ?icSanchez \n"
				+ "			FILTER ( ?icSanchez <= "+ icSanchez +") \n"
				+ "		}\n"
				+ "		?uri rdfs:label|skos:prefLabel|skos:altLabel ?l . \n"
				+ "		?uri ?typelab ?l . \n"
				+ "		BIND ( str(?l) as ?label) \n"
				+ "		BIND ( lang(?l) as ?lang) \n"
				+ "}\n";
		
		
		
		return mapEntityGraph(execSelectQuery(query));
	}
	
	@Override
	public Set<EntityInGraph> findAllEntityInGraph(Set<String> graphs) {
		String query = "\n" 
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "\n"
				+ "select  distinct ?uri ?typelab ?label ?lang\n" 
				+ "{\n"
				+ "		VALUES ?g {"+ toUriList(graphs) + "}\n"
				+ "		GRAPH ?g {\n"
				+ "			?uri ?p ?q . \n"
				+ "		}\n"
				+ "		?uri rdfs:label|skos:prefLabel|skos:altLabel ?l . \n"
				+ "		?uri ?typelab ?l \n"
				+ "		BIND ( str(?l) as ?label) \n"
				+ "		BIND ( lang(?l) as ?lang) \n"

				+ "}\n";
		
		return mapEntityGraph(execSelectQuery(query));
	}
	
	@Override
	public Set<EntityInGraph> findAllEntity() {
		String query = "\n" 
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "\n"
				+ "select  distinct ?uri ?typelab ?label ?lang\n" 
				+ "{\n"
				+ "		GRAPH ?g {\n"
				+ "			?uri ?p ?q . \n"
				+ "		}\n"
				+ "		?uri rdfs:label|skos:prefLabel|skos:altLabel ?l . \n"
				+ "		?uri ?typelab ?l \n"
				+ "		BIND ( str(?l) as ?label) \n"
				+ "		BIND ( lang(?l) as ?lang) \n"

				+ "}\n";
		
		return mapEntityGraph(execSelectQuery(query));
	}
	
	@Override
	public void updateMetricsForEntity(InformationContent ic) {
		remove(ic.getUri(), "http://erias.org/icSanchez", null, ic.getGraph());
		addLiteral(ic.getUri(), "http://erias.org/icSanchez", ic.getIcSanchez().toString(), ic.getGraph());
	}
	
	@Override
	public void updateMetricsForEntity(Set<InformationContent> ic) {
		// TODO Auto-generated method stub
		remove(null, "http://erias.org/icSanchez", null, ic.stream().findFirst().get().getGraph());
		add(ic.stream().map(i -> createLiteralDoubleStatement(i.getUri(), "http://erias.org/icSanchez", i.getIcSanchez(), i.getGraph()))
				.collect(Collectors.toSet()),500);
	}
		
	
	private Set<EntityInGraph> mapEntityGraph(List<BindingSet> res) {
		Set<EntityInGraph> entityInGraphs = new HashSet<EntityInGraph>();
		Set<String> lab = new HashSet<>();
		res.stream()
		.collect(Collectors.groupingBy(b -> b.getValue("uri").stringValue(),
				Collectors.mapping(b -> b, Collectors.toSet())))
		.entrySet()
		.forEach(en -> {
			EntityInGraph entityInGraph = new EntityInGraph();
			en.getValue().forEach(re -> {
				entityInGraph.setUri(re.getValue("uri").stringValue());
				if (re.getValue("typelab").stringValue().equals("http://www.w3.org/2004/02/skos/core#prefLabel")){
					if (re.getValue("lang").stringValue().equals("fr")){
						entityInGraph.setPrefLabel(re.getValue("label").stringValue());
						lab.add("preffr");
					}else {
						if (!lab.contains("preffr")) {
							entityInGraph.setPrefLabel(re.getValue("label").stringValue());
						}
					}
					lab.add("pref");
				}else if (re.getValue("typelab").stringValue().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#label")){
					if(!lab.contains("pref")){
						if (re.getValue("lang").stringValue().equals("fr")){
							entityInGraph.setPrefLabel(re.getValue("label").stringValue());
							lab.add("labfr");
						}else {
							if (!lab.contains("labfr")) {
								entityInGraph.setPrefLabel(re.getValue("label").stringValue());
							}
						}
						lab.add("lab");
					}
				}else {
					if(lab.isEmpty()) {
						if (re.getValue("lang").stringValue().equals("fr")){
							entityInGraph.setPrefLabel(re.getValue("label").stringValue());
							lab.add("otherfr");
						}else {
							if (!lab.contains("otherfr")) {
								entityInGraph.setPrefLabel(re.getValue("label").stringValue());
							}
						}
					}
				}
				entityInGraph.addLabel(new Entitylabel(re.getValue("label").stringValue(), re.getValue("typelab").stringValue(), re.getValue("lang").stringValue()));
			});
			entityInGraphs.add(entityInGraph);
			lab.clear();
		});
		log.info("Number of entity entries : " + entityInGraphs.size());
		return entityInGraphs;
	}


}
