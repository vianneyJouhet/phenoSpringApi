package org.erias.phenoApi.model;

import java.util.Set;

public class PostCohorteGraphICCode {
	private String cohorte;
	private Set<PostGraphIc> graphIc;
	private Set<String> uris;
	
	/**
	 * @return the uris
	 */
	public Set<String> getUris() {
		return uris;
	}
	/**
	 * @param uris the uris to set
	 */
	public void setUris(Set<String> uris) {
		this.uris = uris;
	}
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
