package org.erias.phenoApi.model;

import java.util.Set;

public class ForceGraphData {

	private Set<ForceNode> nodes;
	private Set<ForceLink> links;
	/**
	 * @param forceNodes
	 * @param forceLinks
	 */
	public ForceGraphData(Set<ForceNode> forceNodes, Set<ForceLink> forceLinks) {
		super();
		this.nodes = forceNodes;
		this.links = forceLinks;
	}
	/**
	 * @return the forceNodes
	 */
	public Set<ForceNode> getNodes() {
		return nodes;
	}
	/**
	 * @param forceNodes the forceNodes to set
	 */
	public void setNodes(Set<ForceNode> forceNodes) {
		this.nodes = forceNodes;
	}
	/**
	 * @return the forceLinks
	 */
	public Set<ForceLink> getLinks() {
		return links;
	}
	/**
	 * @param forceLinks the forceLinks to set
	 */
	public void setLinks(Set<ForceLink> forceLinks) {
		this.links = forceLinks;
	}

}
