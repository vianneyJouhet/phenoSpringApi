package org.erias.phenoApi.model;

public class CoocurenceMetric {

	/**
	 * @param code1
	 * @param code2
	 * @param graph1
	 * @param graph2
	 * @param number
	 */
	public CoocurenceMetric(String code1, String code2, String graph1, String graph2, Long number) {
		super();
		this.code1 = code1;
		this.code2 = code2;
		this.graph1 = graph1;
		this.graph2 = graph2;
		this.number = number;
	}

	private String code1;
	private String code2;
	private String graph1;
	private String graph2;
	private Long number;
	
	
	public CoocurenceMetric() {
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * @return the graph1
	 */
	public String getGraph1() {
		return graph1;
	}



	/**
	 * @param graph1 the graph1 to set
	 */
	public void setGraph1(String graph1) {
		this.graph1 = graph1;
	}



	/**
	 * @return the graph2
	 */
	public String getGraph2() {
		return graph2;
	}



	/**
	 * @param graph2 the graph2 to set
	 */
	public void setGraph2(String graph2) {
		this.graph2 = graph2;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CoocurenceMetric [code1=" + code1 + ", code2=" + code2 + ", graph1=" + graph1 + ", graph2=" + graph2
				+ ", number=" + number + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code1 == null) ? 0 : code1.hashCode());
		result = prime * result + ((code2 == null) ? 0 : code2.hashCode());
		result = prime * result + ((graph1 == null) ? 0 : graph1.hashCode());
		result = prime * result + ((graph2 == null) ? 0 : graph2.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		CoocurenceMetric other = (CoocurenceMetric) obj;
		if (code1 == null) {
			if (other.code1 != null)
				return false;
		} else if (!code1.equals(other.code1))
			return false;
		if (code2 == null) {
			if (other.code2 != null)
				return false;
		} else if (!code2.equals(other.code2))
			return false;
		if (graph1 == null) {
			if (other.graph1 != null)
				return false;
		} else if (!graph1.equals(other.graph1))
			return false;
		if (graph2 == null) {
			if (other.graph2 != null)
				return false;
		} else if (!graph2.equals(other.graph2))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	/**
	 * @return the code1
	 */
	public String getCode1() {
		return code1;
	}

	/**
	 * @param code1 the code1 to set
	 */
	public void setCode1(String code1) {
		this.code1 = code1;
	}

	/**
	 * @return the code2
	 */
	public String getCode2() {
		return code2;
	}

	/**
	 * @param code2 the code2 to set
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}

	/**
	 * @return the number
	 */
	public Long getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Long number) {
		this.number = number;
	}



}
