package org.erias.phenoApi.model;

public class ICFeature {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ICFeature [uri=" + uri + ", leaves=" + leaves + ", subsumers=" + subsumers + ", graph=" + graph + "]";
	}
	protected String uri;
	protected Long leaves;
	protected Long subsumers;
	private String graph;
	
	public ICFeature() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param uri
	 * @param leanves
	 * @param subsumers
	 */
	public ICFeature(String uri, Long leaves, Long subsumers,String graph) {
		super();
		this.uri = uri;
		this.leaves = leaves;
		this.subsumers = subsumers;
		this.graph=graph;
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
	 * @return the leanves
	 */
	public Long getLeaves() {
		return leaves;
	}
	/**
	 * @param leanves the leanves to set
	 */
	public void setLeaves(Long leanves) {
		this.leaves = leanves;
	}
	/**
	 * @return the subsumers
	 */
	public Long getSubsumers() {
		return subsumers;
	}
	/**
	 * @param subsumers the subsumers to set
	 */
	public void setSubsumers(Long subsumers) {
		this.subsumers = subsumers;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + ((leaves == null) ? 0 : leaves.hashCode());
		result = prime * result + ((subsumers == null) ? 0 : subsumers.hashCode());
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
		ICFeature other = (ICFeature) obj;
		if (graph == null) {
			if (other.graph != null)
				return false;
		} else if (!graph.equals(other.graph))
			return false;
		if (leaves == null) {
			if (other.leaves != null)
				return false;
		} else if (!leaves.equals(other.leaves))
			return false;
		if (subsumers == null) {
			if (other.subsumers != null)
				return false;
		} else if (!subsumers.equals(other.subsumers))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

}
