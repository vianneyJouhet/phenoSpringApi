package org.erias.phenoApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ThesaurusFrequency {

	@Id
	@Column(name="code")
	private String code;
	@Column(name="frequency")
	private long frequency;
	
	protected ThesaurusFrequency() {}

	/**
	 * @param uri
	 * @param frequency
	 */
	public ThesaurusFrequency(String code, long frequency) {
		super();
		this.code = code;
		this.frequency = frequency;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (frequency ^ (frequency >>> 32));
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		ThesaurusFrequency other = (ThesaurusFrequency) obj;
		if (frequency != other.frequency)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	/**
	 * @return the uri
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the frequency
	 */
	public long getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ThesaurusFrequency [code=" + code + ", frequency=" + frequency + "]";
	}
	
}
