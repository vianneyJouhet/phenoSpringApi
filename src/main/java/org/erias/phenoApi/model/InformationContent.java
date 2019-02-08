package org.erias.phenoApi.model;

public class InformationContent extends ICFeature {

	private Long maxLeaves;
	private Float icSeco;
	private Float icZhou;
	private Double icSanchez;


	/**
	 * 
	 */
	public InformationContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InformationContent(ICFeature icFeature,Long maxLeaves) {
		super(icFeature.getUri(),icFeature.getLeaves(),icFeature.getSubsumers(),icFeature.getGraph());
		this.maxLeaves=maxLeaves;
		this.icSanchez = calculateIcSanchez();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param uri
	 * @param leanves
	 * @param subsumers
	 */
	
	
	public InformationContent(String uri, Long leaves, Long subsumers, Long maxLeaves,String graph) {
		super(uri, leaves, subsumers,graph);
		// TODO Auto-generated constructor stub
		
		this.icSanchez = calculateIcSanchez();
	}

	
	/**
	 * @param uri
	 * @param icSeco
	 * @param icZhou
	 * @param icSanchez
	 */
	public InformationContent(String uri, Float icSeco, Float icZhou, Double icSanchez) {
		super();
		this.icSeco = icSeco;
		this.icZhou = icZhou;
		this.icSanchez = icSanchez;
	}

	private Double calculateIcSanchez() {
		Double a = (double)  leaves/ subsumers;
		Double b = (double) a +1;
		Double nume = (double) Math.log(b);
		Double c = (double) maxLeaves +1;
		Double demom = (double) Math.log(c);
		return (double) 1-nume/demom;
		
//		return (float) -Math.log(d)/10;
	}

	/**
	 * @return the maxLeaves
	 */
	public Long getMaxLeaves() {
		return maxLeaves;
	}

	/**
	 * @param maxLeaves the maxLeaves to set
	 */
	public void setMaxLeaves(Long maxLeaves) {
		this.maxLeaves = maxLeaves;
	}

	/**
	 * @return the icSeco
	 */
	public Float getIcSeco() {
		return icSeco;
	}

	/**
	 * @param icSeco the icSeco to set
	 */
	public void setIcSeco(Float icSeco) {
		this.icSeco = icSeco;
	}

	/**
	 * @return the icZhou
	 */
	public Float getIcZhou() {
		return icZhou;
	}

	/**
	 * @param icZhou the icZhou to set
	 */
	public void setIcZhou(Float icZhou) {
		this.icZhou = icZhou;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((icSanchez == null) ? 0 : icSanchez.hashCode());
		result = prime * result + ((icSeco == null) ? 0 : icSeco.hashCode());
		result = prime * result + ((icZhou == null) ? 0 : icZhou.hashCode());
		result = prime * result + ((maxLeaves == null) ? 0 : maxLeaves.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InformationContent other = (InformationContent) obj;
		if (icSanchez == null) {
			if (other.icSanchez != null)
				return false;
		} else if (!icSanchez.equals(other.icSanchez))
			return false;
		if (icSeco == null) {
			if (other.icSeco != null)
				return false;
		} else if (!icSeco.equals(other.icSeco))
			return false;
		if (icZhou == null) {
			if (other.icZhou != null)
				return false;
		} else if (!icZhou.equals(other.icZhou))
			return false;
		if (maxLeaves == null) {
			if (other.maxLeaves != null)
				return false;
		} else if (!maxLeaves.equals(other.maxLeaves))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InformationContent [maxLeaves=" + maxLeaves + ", icSeco=" + icSeco + ", icZhou=" + icZhou
				+ ", icSanchez=" + icSanchez + ", uri=" + uri + ", leaves=" + leaves + ", subsumers=" + subsumers + "]";
	}
	

	
}
