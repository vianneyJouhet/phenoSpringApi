package org.erias.phenoApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name="indexdoc")

public class IndexDoc {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator= "indexDoc_generator")
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
	@Formula("concat(patientnum, cohorte)") 
	private String patientId;
	protected IndexDoc() {}
	
	/**
	 * @param patientNum
	 * @param documentNum
	 * @param certainty
	 * @param context
	 * @param code
	 * @param term
	 * @param termfoundInText
	 */
	public IndexDoc(String patientNum, String documentNum, String certainty, String context, String code, String term,
			String termfoundInText, String cohorte) {
		super();
		this.patientNum = patientNum;
		this.documentNum = documentNum;
		this.certainty = certainty;
		this.context = context;
		this.code = code;
		this.term = term;
		this.termfoundInText = termfoundInText;
		this.cohorte = cohorte;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndexDoc [Id=" + id + ", PatientNum=" + patientNum + ", DocumentNum=" + documentNum + ", Certainty="
				+ certainty + ", Context=" + context + ", Code=" + code + ", Term=" + term + ", TermfoundInText="
				+ termfoundInText + "]";
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
		id = id;
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
		patientNum = patientNum;
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
		documentNum = documentNum;
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
		certainty = certainty;
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
		context = context;
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
		code = code;
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
		term = term;
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
		termfoundInText = termfoundInText;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		IndexDoc other = (IndexDoc) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
	
	
	
	
}
