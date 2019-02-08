package org.erias.phenoApi.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CoocurenceMatrix {

	protected static final Logger log = LogManager.getLogger(CoocurenceMatrix.class);

	private Map<String,Set<SortedSet<EntityInGraph>>> coocurences=new HashMap<String,Set<SortedSet<EntityInGraph>>>();
	
	public CoocurenceMatrix() {
		// TODO Auto-generated constructor stub
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public void addCoocurence(String code1,String code2,String id,String graph1,String graph2,String label1,String label2) {
			
			
			Set<SortedSet<EntityInGraph>> paires=getPaires(id);
			SortedSet<EntityInGraph> newPaire = new TreeSet<EntityInGraph>();
			newPaire.add(new EntityInGraph(code1, label1,graph1));
			newPaire.add(new EntityInGraph(code2, label2,graph2));
			
			paires.add(newPaire);
			coocurences.put(id, paires);
	}
	
	public Set<SortedSet<EntityInGraph>> getPaires (String id){
		if (coocurences.containsKey(id)){
			return coocurences.get(id);
		}
		else {
			return new HashSet<SortedSet<EntityInGraph>>(); 
		}
	}
	
	public void addAllCoocurencesFromIndexDocs(List<IndexDocIdf> indexDocs, EntityHierarchie entityHierarchie,String graph) {
		Map<String, Set<String>> listCode = indexDocs
				.stream()
				.map(x -> new IndexDoc(x.getPatientNum(), null, null, null, x.getCode(), null, null, null))
				.distinct()
				.collect(Collectors.groupingBy(IndexDoc::getPatientNum,
							Collectors.flatMapping(x -> entityHierarchie.getDeepestAncestors(x.getCode()).stream(),Collectors.toSet())));
		indexDocs.forEach(i -> {
			entityHierarchie.getDeepestAncestors(i.getCode())
				.forEach(code1 -> {
					listCode.get(i.getPatientNum()).forEach(code2 -> {
						if (!code1.equals(code2)){
								addCoocurence(code1,code2,i.getPatientNum(),graph,graph,null,null);
							}
					});
				});
		});
		
	}
	
	public void addAllCoocurencesFromIndexDocs(List<IndexDocIdf> indexDocs,EntityHierarchie entityHierarchie,String graph1,List<IndexDocIdf> indexDocs2,EntityHierarchie entityHierarchie2,String graph2 ) {
		Map<String, Set<String>> listCode = indexDocs
				.stream()
				.map(x -> new IndexDoc(x.getPatientNum(), null,null, null, x.getCode(), null, null, null))
				.distinct()
				.collect(Collectors.groupingBy(IndexDoc::getPatientNum,
							Collectors.flatMapping(x -> entityHierarchie.getDeepestAncestors(x.getCode()).stream(),Collectors.toSet())));
		indexDocs2.forEach(i -> {
			entityHierarchie2.getDeepestAncestors(i.getCode())
				.forEach(code2 -> {
					if(listCode.containsKey(i.getPatientNum())) {
						listCode.get(i.getPatientNum()).forEach(code1 -> {
							if (!code1.equals(code2)){
								addCoocurence(code1,code2,i.getPatientNum(),graph1,graph2,null,null);
							}
						});
					}
				});
		});
		
	}
	
	public  Set<Entry<String, Set<SortedSet<EntityInGraph>>>> entrySet() {
		return coocurences.entrySet();
	}
	
	public  Set<String> keySet() {
		return coocurences.keySet();
	}
	
	public  Set<SortedSet<EntityInGraph>> pairesSet() {
		return coocurences.values().stream().flatMap(x -> x.stream()).collect(Collectors.toSet());
	}
	
	public  List<SortedSet<EntityInGraph>> pairesList() {
		return coocurences.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
	}
	
	public  Set<CoocurenceMetric> computeMetrics() {
		Set<CoocurenceMetric> coocurenceMetrics = new HashSet<CoocurenceMetric>();
		pairesList().stream()
		.collect(Collectors.groupingBy(x -> x	,Collectors.counting()))
		.entrySet()
		.forEach(e -> {
			coocurenceMetrics.add(new CoocurenceMetric(
					e.getKey().first().getUri(),
					e.getKey().last().getUri(),
					e.getKey().first().getGraph(), 
					e.getKey().last().getGraph(), 
					e.getValue()));
		});
		
		return coocurenceMetrics;
	}
	
	
	
	

}