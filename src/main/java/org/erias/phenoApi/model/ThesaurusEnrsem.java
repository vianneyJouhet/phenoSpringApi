package org.erias.phenoApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="thesaurus_enrsem")
public class ThesaurusEnrsem extends ThesaurusFrequency{

	@Column(name="label")
	private String label;
	@Column(name="idf")
	private float idf;
	
	
	protected ThesaurusEnrsem() {}

	
	/**
	 * @param code
	 * @param frequency
	 * @param label
	 */
	public ThesaurusEnrsem(String code, long frequency,String label) {
		super(code, frequency);
		// TODO Auto-generated constructor stub
		this.label = label;
	}
	
	/**
	 * @param code
	 * @param frequency
	 * @param label
	 * @param totalPat
	 */
	public ThesaurusEnrsem(String code, long frequency,String label, long totalPat) {
		super(code, frequency);
		// TODO Auto-generated constructor stub
		this.label = label;
		this.idf = (float) Math.log((float)totalPat/frequency);
	}
	
	/**
	 * @param code
	 * @param frequency
	 * @param label
	 * @param totalPat
	 */
	public ThesaurusEnrsem(String code, long frequency,String label, float idf) {
		super(code, frequency);
		// TODO Auto-generated constructor stub
		this.label = label;
		this.idf = idf;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ThesaurusEnrsem [getLabel()=" + getLabel() + ", getIdf()=" + getIdf() + ", hashCode()=" + hashCode()
				+ ", getCode()=" + getCode() + ", getFrequency()=" + getFrequency() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + "]";
	}


	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the idf
	 */
	public double getIdf() {
		return idf;
	}


	/**
	 * @param idf the idf to set
	 */
	public void setIdf(float idf) {
		this.idf = idf;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		ThesaurusEnrsem other = (ThesaurusEnrsem) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}



	
	
}
