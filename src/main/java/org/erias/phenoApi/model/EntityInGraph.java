package org.erias.phenoApi.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityInGraph implements Comparable{

	private String uri;
	private String prefLabel;
	private Set<Entitylabel> labels;
	private int level;
	private Double icSanchez;
	private String graph;
	
	public EntityInGraph() {
		// TODO Auto-generated constructor stub
		this.labels = new HashSet<Entitylabel>();
		
	}
	
	/**
	 * @param uri
	 * @param prefLabel
	 * @param labels
	 * @param level
	 * @param icSanchez
	 * @param graph
	 */
	public EntityInGraph(String uri, String prefLabel, Set<Entitylabel> labels, int level, Double icSanchez, String graph) {
		super();
		this.uri = uri;
		this.prefLabel = prefLabel;
		this.labels = labels;
		this.level = level;
		this.icSanchez = icSanchez;
		this.graph = graph;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + ((icSanchez == null) ? 0 : icSanchez.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + level;
		result = prime * result + ((prefLabel == null) ? 0 : prefLabel.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityInGraph other = (EntityInGraph) obj;
		if (graph == null) {
			if (other.graph != null)
				return false;
		} else if (!graph.equals(other.graph))
			return false;
		if (icSanchez == null) {
			if (other.icSanchez != null)
				return false;
		} else if (!icSanchez.equals(other.icSanchez))
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (level != other.level)
			return false;
		if (prefLabel == null) {
			if (other.prefLabel != null)
				return false;
		} else if (!prefLabel.equals(other.prefLabel))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	/**
	 * @param uri
	 * @param prefLabel
	 * @param labels
	 * @param level
	 * @param icSanchez
	 * @param graph
	 */
	public EntityInGraph(String uri, String prefLabel, String graph) {
		super();
		this.uri = uri;
		this.prefLabel = prefLabel;
		this.graph = graph;
	}
	
	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}


	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}


	/**
	 * @return the prefLabel
	 */
	public String getPrefLabel() {
		return prefLabel;
	}


	/**
	 * @param prefLabel the prefLabel to set
	 */
	public void setPrefLabel(String prefLabel) {
		this.prefLabel = prefLabel;
	}


	/**
	 * @return the labels
	 */
	public Set<Entitylabel> getLabels() {
		return labels;
	}


	/**
	 * @param labels the labels to set
	 */
	public void setLabels(Set<Entitylabel> labels) {
		this.labels = labels;
	}
	
	public void addLabel(Entitylabel label) {
		this.labels.add(label);
	}


	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}


	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}


	/**
	 * @return the icSanchez
	 */
	public Double getIcSanchez() {
		return icSanchez;
	}


	/**
	 * @param icSanchez the icSanchez to set
	 */
	public void setIcSanchez(Double icSanchez) {
		this.icSanchez = icSanchez;
	}


	/**
	 * @return the graph
	 */
	public String getGraph() {
		return graph;
	}


	/**
	 * @param graph the graph to set
	 */
	public void setGraph(String graph) {
		this.graph = graph;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EntityInGraph [uri=" + uri + ", prefLabel=" + prefLabel + ", labels=" + labels + ", level=" + level
				+ ", icSanchez=" + icSanchez + ", graph=" + graph + "]";
	}


	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return this.getUri().compareTo(((EntityInGraph) o).getUri());
	}


	


	

}
