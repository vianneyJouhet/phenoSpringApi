package org.erias.phenoApi.model;

import java.util.Set;

import javax.persistence.Entity;

public class PostGetCoocurence {

	private String cohorte;
	private Set<String> uris;
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
}
