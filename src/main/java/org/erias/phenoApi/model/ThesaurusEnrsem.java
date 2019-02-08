package org.erias.phenoApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "thesaurus_enrsem")
public class ThesaurusEnrsem {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator= "indexDoc_generator")
	@Column(name = "Id")
	private Long id;
	@Column(name = "code")
	private String code;
	@Column(name = "frequency")
	private long frequency;
	@Column(name = "label")
	private String label;
	@Column(name = "idf")
	private float idf;
	@Column(name= "inferedMetrics")
	private boolean inferedMetrics;
	@Column(name="graph")
	private String graph;
	@Column(name = "icseco")
	private Float icSeco;
	@Column(name = "iczhou")
	private Float icZhou;
	@Column(name = "icsanchez")
	private Double icSanchez;

	protected ThesaurusEnrsem() {
	}

	/**
	 * @param code
	 * @param frequency
	 * @param label
	 * @param idf
	 * @param icSeco
	 * @param icZhou
	 * @param icSanchez
	 */
	public ThesaurusEnrsem(String code, long frequency, String label, float idf, Float icSeco, Float icZhou,
			Double icSanchez) {
		super();
		this.code = code;
		this.frequency = frequency;
		this.label = label;
		this.idf = idf;
		this.icSeco = icSeco;
		this.icZhou = icZhou;
		this.icSanchez = icSanchez;
		this.inferedMetrics = false;
	}
	
	/**
	 * @param code
	 * @param frequency
	 * @param label
	 * @param idf
	 * @param icSeco
	 * @param icZhou
	 * @param icSanchez
	 * @param inferedMetrics
	 */
	public ThesaurusEnrsem(String code, long frequency, String label, float idf, Float icSeco, Float icZhou,
			Double icSanchez, boolean inferedMetrics) {
		super();
		this.code = code;
		this.frequency = frequency;
		this.label = label;
		this.idf = idf;
		this.icSeco = icSeco;
		this.icZhou = icZhou;
		this.icSanchez = icSanchez;
		this.inferedMetrics = inferedMetrics;
	}

	public ThesaurusEnrsem(String code, long frequency, String label) {
		this.code = code;
		this.frequency = frequency;
		// TODO Auto-generated constructor stub
		this.label = label;
	}
	
	public ThesaurusEnrsem(String code, long frequency) {
		this.code = code;
		this.frequency = frequency;
	}

	/**
	 * @param code
	 * @param frequency
	 * @param label
	 * @param totalPat
	 */
	public ThesaurusEnrsem(String code, long frequency, String label, long totalPat) {
		this.code = code;
		this.frequency = frequency;
		this.inferedMetrics = false;
		// TODO Auto-generated constructor stub
		this.label = label;
		if (frequency==0) {
			idf=-1;
		}else {
			calculateIdf(totalPat);
		}
	}
	
	/**
	 * @param code
	 * @param frequency
	 * @param label
	 * @param totalPat
	 */
	public ThesaurusEnrsem(String code, long frequency, String label, long totalPat,boolean inferredMetrics,String graph) {
		this.code = code;
		this.frequency = frequency;
		this.inferedMetrics = inferredMetrics;
		this.graph=graph;
		// TODO Auto-generated constructor stub
		this.label = label;
		if (frequency==0) {
			idf=-1;
		}else {
			calculateIdf(totalPat);
		}
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

	public void calculateIdf(long totalPat) {
		this.idf = (float) Math.log((float) totalPat / frequency);
	}
	
	public ThesaurusEnrsem(String code, long frequency, String label, long totalPat, InformationContent icContent) {
		this.code = code;
		this.frequency = frequency;
		// TODO Auto-generated constructor stub
		this.label = label;
		this.idf = (float) Math.log((float) totalPat / frequency);
		if (icContent != null) {
			this.icSanchez = icContent.getIcSanchez();
			this.icZhou = icContent.getIcZhou();
			this.icSeco = icContent.getIcSeco();
		}
	}

	/**
	 * @param code
	 * @param frequency
	 * @param label
	 * @param totalPat
	 */
	public ThesaurusEnrsem(String code, long frequency, String label, float idf) {
		this.code = code;
		this.frequency = frequency;
		// TODO Auto-generated constructor stub
		this.label = label;
		this.idf = idf;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ThesaurusEnrsem [code=" + code + ", frequency=" + frequency + ", label=" + label + ", idf=" + idf
				+ ", inferedMetrics=" + inferedMetrics + ", graph=" + graph + ", icSeco=" + icSeco + ", icZhou="
				+ icZhou + ", icSanchez=" + icSanchez + "]";
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
	public float getIdf() {
		return idf;
	}

	/**
	 * @param idf the idf to set
	 */
	public void setIdf(float idf) {
		this.idf = idf;
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + (int) (frequency ^ (frequency >>> 32));
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + ((icSanchez == null) ? 0 : icSanchez.hashCode());
		result = prime * result + ((icSeco == null) ? 0 : icSeco.hashCode());
		result = prime * result + ((icZhou == null) ? 0 : icZhou.hashCode());
		result = prime * result + Float.floatToIntBits(idf);
		result = prime * result + (inferedMetrics ? 1231 : 1237);
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThesaurusEnrsem other = (ThesaurusEnrsem) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (frequency != other.frequency)
			return false;
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
		if (Float.floatToIntBits(idf) != Float.floatToIntBits(other.idf))
			return false;
		if (inferedMetrics != other.inferedMetrics)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

}