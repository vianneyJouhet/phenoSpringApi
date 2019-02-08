package org.erias.phenoApi.model;

public class Entitylabel {



	

	private String label;
	private String type;
	private String lang;

	
	public Entitylabel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param type
	 * @param lang
	 */
	public Entitylabel(String label, String type, String lang) {
		super();
		this.label = label;
		this.type = type;
		this.setLang(lang);
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	

}
