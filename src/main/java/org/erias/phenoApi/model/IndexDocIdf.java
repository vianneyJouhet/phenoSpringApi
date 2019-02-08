package org.erias.phenoApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;




@Entity
@Table(name = "indexdocidf")

public class IndexDocIdf {

	@Id
	@Column(name="id")
	private Long id;
	@Column(name="patientnum")
	private String patientNum;
	@Column(name="documentnum")
	private String documentNum;
	@Column(name="certainty")
	private String certainty;
	@Column(name="context")
	private String context;
	@Column(name="code")
	private String code;
	@Column(name="term")
	private String term;
	@Column(name="termfoundintext")
	private String termfoundInText;
	@Column(name="cohorte")
	private String cohorte;
	@Column(name="label")
	private String label;
	@Column(name="idf")
	private float idf;
	@Column(name="frequency")
	private long frequency;
	@Column(name="icsanchez")
	private Double icSanchez;
	@Column(name="inferedMetrics")
	private boolean inferedMetrics;
	
	protected IndexDocIdf() {}

	/**
	 * @param id
	 * @param patientNum
	 * @param documentNum
	 * @param certainty
	 * @param context
	 * @param code
	 * @param term
	 * @param termfoundInText
	 * @param cohorte
	 * @param label
	 * @param idf
	 * @param frequency
	 */
	public IndexDocIdf(Long id, String patientNum, String documentNum, String certainty, String context, String code,
			String term, String termfoundInText, String cohorte, String label, float idf, long frequency,boolean inferedMetrics) {
		super();
		this.id = id;
		this.patientNum = patientNum;
		this.documentNum = documentNum;
		this.certainty = certainty;
		this.context = context;
		this.code = code;
		this.term = term;
		this.termfoundInText = termfoundInText;
		this.cohorte = cohorte;
		if (label == null) {
			this.label = "null";
		}else {
			this.label = label;
		}
		this.idf = idf;
		this.frequency = frequency;
		this.inferedMetrics = inferedMetrics;
	}

	/**
	 * @return the inferedMetrics
	 */
	public boolean isInferedMetrics() {
		return inferedMetrics;
	}

	/**
	 * @param inferedMetrics the inferedMetrics to set
	 */
	public void setInferedMetrics(boolean inferedMetrics) {
		this.inferedMetrics = inferedMetrics;
	}

	public IndexDocIdf(String code, String label) {
		this.code=code;
		this.label=label;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndexDocIdf [id=" + id + ", patientNum=" + patientNum + ", documentNum=" + documentNum + ", certainty="
				+ certainty + ", context=" + context + ", code=" + code + ", term=" + term + ", termfoundInText="
				+ termfoundInText + ", cohorte=" + cohorte + ", label=" + label + ", idf=" + idf + ", frequency="
				+ frequency + ", icSanchez=" + icSanchez + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((certainty == null) ? 0 : certainty.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((cohorte == null) ? 0 : cohorte.hashCode());
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((documentNum == null) ? 0 : documentNum.hashCode());
		result = prime * result + (int) (frequency ^ (frequency >>> 32));
		result = prime * result + ((icSanchez == null) ? 0 : icSanchez.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(idf);
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((patientNum == null) ? 0 : patientNum.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		result = prime * result + ((termfoundInText == null) ? 0 : termfoundInText.hashCode());
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
		IndexDocIdf other = (IndexDocIdf) obj;
		if (certainty == null) {
			if (other.certainty != null)
				return false;
		} else if (!certainty.equals(other.certainty))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (cohorte == null) {
			if (other.cohorte != null)
				return false;
		} else if (!cohorte.equals(other.cohorte))
			return false;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context))
			return false;
		if (documentNum == null) {
			if (other.documentNum != null)
				return false;
		} else if (!documentNum.equals(other.documentNum))
			return false;
		if (frequency != other.frequency)
			return false;
		if (icSanchez == null) {
			if (other.icSanchez != null)
				return false;
		} else if (!icSanchez.equals(other.icSanchez))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Float.floatToIntBits(idf) != Float.floatToIntBits(other.idf))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (patientNum == null) {
			if (other.patientNum != null)
				return false;
		} else if (!patientNum.equals(other.patientNum))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		if (termfoundInText == null) {
			if (other.termfoundInText != null)
				return false;
		} else if (!termfoundInText.equals(other.termfoundInText))
			return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the patientNum
	 */
	public String getPatientNum() {
		return patientNum;
	}

	/**
	 * @param patientNum the patientNum to set
	 */
	public void setPatientNum(String patientNum) {
		this.patientNum = patientNum;
	}

	/**
	 * @return the documentNum
	 */
	public String getDocumentNum() {
		return documentNum;
	}

	/**
	 * @param documentNum the documentNum to set
	 */
	public void setDocumentNum(String documentNum) {
		this.documentNum = documentNum;
	}

	/**
	 * @return the certainty
	 */
	public String getCertainty() {
		return certainty;
	}

	/**
	 * @param certainty the certainty to set
	 */
	public void setCertainty(String certainty) {
		this.certainty = certainty;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return the termfoundInText
	 */
	public String getTermfoundInText() {
		return termfoundInText;
	}

	/**
	 * @param termfoundInText the termfoundInText to set
	 */
	public void setTermfoundInText(String termfoundInText) {
		this.termfoundInText = termfoundInText;
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
	

}