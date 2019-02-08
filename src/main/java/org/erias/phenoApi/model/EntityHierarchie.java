package org.erias.phenoApi.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;

public class EntityHierarchie {

	private Map<String, Set<String>> ancestors;
	private Map<String, Set<String>> deepestAncestorsSign;
	private Map<String, Set<String>> deepestAncestors;
	private Map<String, Set<String>> childs;


	protected static final Logger log = LogManager.getLogger(EntityHierarchie.class);

	public EntityHierarchie() {
		// TODO Auto-generated constructor stub
		this.ancestors = new HashMap<String, Set<String>>();
		this.childs = new HashMap<String, Set<String>>();
		this.deepestAncestors = new HashMap<String, Set<String>>();
		this.deepestAncestorsSign = new HashMap<String, Set<String>>();
	}

	public EntityHierarchie addAndGetEntityHierarchie(String ancestor, String children) {
		addEntityHierarchie(ancestor, children);
		return this;
	}

	public void addEntityHierarchie(String ancestor, String children) {
//		if (!ancestor.equals(children)) {
			Set<String> pereSet = this.getAncestors(children,true);
			Set<String> filsSet = this.getChilds(ancestor,true);
			pereSet.add(ancestor);
			ancestors.put(children, pereSet);
			filsSet.add(children);
			childs.put(ancestor, filsSet);
//		}
	}

	public Set<String> getAncestors(String fils,boolean getSelf) {
		if (ancestors.containsKey(fils)) {
			if (getSelf) {
				return ancestors.get(fils);
			}else {
				return ancestors.get(fils).stream().filter(x -> !x.equals(fils)).collect(Collectors.toSet());
			}
		} else {
			return new HashSet<String>();
		}

	}
	
	public Set<String> getDeepestAncestors(String fils) {
		if (deepestAncestors.containsKey(fils)) {
			return deepestAncestors.get(fils);
		} else {
			return new HashSet<String>();
		}

	}

	public Set<String> computeDeepestAncestors(String fils) {
			if(!deepestAncestorsSign.containsKey(getAncestors(fils, false).stream().sorted().reduce("",String::concat))) {
				Set<String> deepest = getAncestors(fils, false).stream()
						.filter(a -> 
						Collections.disjoint(
								getAncestors(fils, false),
								getChilds(a, false)))
						.collect(Collectors.toSet());
				deepestAncestorsSign.put(getAncestors(fils, false).stream().sorted().reduce("",String::concat), deepest);
				return deepest;
			}else {
				return deepestAncestorsSign.get(getAncestors(fils, false).stream().sorted().reduce("",String::concat));
			}
			
			
					

	}
	
	
	public Set<String> getAllChildsNotAncestors() {
		return getAllChilds().stream().filter(f -> !getAllAncestors().contains(f)).collect(Collectors.toSet());
	}
	
	public EntityHierarchie setDeepestAncestors() {
		StopWatch sw = new StopWatch();
		sw.start("Compute-ancestors");		
		log.info("Compute deepest");
		log.info("ancestors size " + getAllAncestors().size());
		log.info("childs size " + getAllChildsNotAncestors().size());
		
		//			log.info(
		if (getAllChildsNotAncestors().size()>getAllAncestors().size()) {
			ancestors.values()
			.parallelStream()
			.distinct()
			.forEach(ancestors -> {
				Set<String> deepest=ancestors.parallelStream()
						.filter(a -> 
						Collections.disjoint(
							ancestors,
							getChilds(a, false))
						)
					.collect(Collectors.toSet());
				deepestAncestorsSign.put(
						ancestors.stream().sorted().reduce("",String::concat),deepest);
			});
		}
	sw.stop();
	sw.start("Compute-deepest");
			getAllChilds().parallelStream() 
			.forEach(f ->  {
				if (getAllAncestors().contains(f)){
					HashSet<String> deepest = new HashSet<String>();
					deepest.add(f);
					deepestAncestors.put(f,deepest);
				}else {
					deepestAncestors.put(f,computeDeepestAncestors(f));
				}
			});
			sw.stop();
			log.info(sw.prettyPrint());
			log.info("end deepest");
			log.info("deepestAncestors " + deepestAncestors.size());
			log.info("deepestAncestorSign " + deepestAncestorsSign.size());
			return this;
	}

	public Set<String> getChilds(String pere,boolean getSelf) {
		if (childs.containsKey(pere)) {
			if (getSelf) {
				return childs.get(pere);
			}else {
				return childs.get(pere).stream().filter(x -> !x.equals(pere)).collect(Collectors.toSet());
			}
		} else {
			return new HashSet<String>();
		}
	}

	public Set<String> getAllChilds() {
		return ancestors.keySet();
	}

	public Set<String> getAllAncestors() {
		return childs.keySet();
	}

}
