package org.erias.phenoApi.model;

import java.util.Set;

public class JQCloud {
	private Float weight;
	private String text;
	private Set<String> uri;
	
	/**
	 * @param weigth
	 * @param text
	 * @param uri
	 */
	public JQCloud(Float weigth, String text, Set<String> uri) {
		super();
		this.weight = weigth;
		this.text = text;
		this.uri = uri;
	}
	
	/**
	 * @return the weight
	 */
	public Float getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the uri
	 */
	public Set<String> getUri() {
		return uri;
	}
	/**
	 * @param uri the uri to set
	 */
	public void setUri(Set<String> uri) {
		this.uri = uri;
	}
	
	
	
}
