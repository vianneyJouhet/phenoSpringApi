package org.erias.phenoApi.model;

import java.util.Set;

public class Post2CohorteGraphIC {
	private String cohorte;
	private Set<PostGraphIc> graphIc;
	
	/**
	 * @return the cohorte
	 */
	public String getCohorte() {
		return cohorte;
	}
	/**
	 * @param cohorte the cohorte to set
	 */
	public void setCohorte(String cohorte) {
		this.cohorte = cohorte;
	}
	/**
	 * @return the graphIc
	 */
	public Set<PostGraphIc> getGraphIc() {
		return graphIc;
	}
	/**
	 * @param graphIc the graphIc to set
	 */
	public void setGraphIc(Set<PostGraphIc> graphIc) {
		this.graphIc = graphIc;
	}
	}
