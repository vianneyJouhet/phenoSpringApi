package org.erias.phenoApi.model;

public class EntityContext {
	private String uri;
	private String graph;
	
	public EntityContext(String uri, String graph) {
		super();
		this.uri = uri;
		this.graph = graph;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getGraph() {
		return graph;
	}
	public void setGraph(String graph) {
		this.graph = graph;
	}
}
