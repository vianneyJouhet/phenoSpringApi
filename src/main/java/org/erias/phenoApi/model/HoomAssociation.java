package org.erias.phenoApi.model;

import java.util.HashSet;
import java.util.Set;

public class HoomAssociation {
	
	private String uriAssociation;
	private String uriSubject;
	private String uriObject;
	private Set<String> uriInferredObject;
	private Set<String> uriPereObject;
	private String uriFrequency;
	private String labelFrequency;
	
	/**
	 * @param uriAssociation
	 * @param uriSubject
	 * @param uriObject
	 * @param uriFrequency
	 * @param labelFrequency
	 */
	public HoomAssociation(String uriAssociation, String uriSubject, String uriObject, String uriFrequency,
			String labelFrequency) {
		super();
		this.uriAssociation = uriAssociation;
		this.uriSubject = uriSubject;
		this.uriObject = uriObject;
		this.uriFrequency = uriFrequency;
		this.labelFrequency = labelFrequency;
		this.uriInferredObject = new HashSet<String>();
		this.uriPereObject = new HashSet<String>();
	}
	
	/**
	 * @param uriAssociation
	 * @param uriSubject
	 * @param uriObject
	 * @param uriInferredObject
	 * @param uriFrequency
	 * @param labelFrequency
	 */
	public HoomAssociation(String uriAssociation, String uriSubject, String uriObject, Set<String> uriInferredObject,
			Set<String> uriPereObject,
			String uriFrequency, String labelFrequency) {
		super();
		this.uriAssociation = uriAssociation;
		this.uriSubject = uriSubject;
		this.uriObject = uriObject;
		this.uriInferredObject = uriInferredObject;
		this.uriPereObject = uriPereObject;
		this.uriFrequency = uriFrequency;
		this.labelFrequency = labelFrequency;
	}

	/**
	 * @return the uriAssociation
	 */
	public String getUriAssociation() {
		return uriAssociation;
	}
	/**
	 * @param uriAssociation the uriAssociation to set
	 */
	public void setUriAssociation(String uriAssociation) {
		this.uriAssociation = uriAssociation;
	}
	/**
	 * @return the uriSubject
	 */
	public String getUriSubject() {
		return uriSubject;
	}
	/**
	 * @param uriSubject the uriSubject to set
	 */
	public void setUriSubject(String uriSubject) {
		this.uriSubject = uriSubject;
	}
	/**
	 * @return the uriObject
	 */
	public String getUriObject() {
		return uriObject;
	}
	/**
	 * @param uriObject the uriObject to set
	 */
	public void setUriObject(String uriObject) {
		this.uriObject = uriObject;
	}
	/**
	 * @return the uriFrequency
	 */
	public String getUriFrequency() {
		return uriFrequency;
	}
	/**
	 * @param uriFrequency the uriFrequency to set
	 */
	public void setUriFrequency(String uriFrequency) {
		this.uriFrequency = uriFrequency;
	}
	/**
	 * @return the labelFrequency
	 */
	public String getLabelFrequency() {
		return labelFrequency;
	}
	/**
	 * @param labelFrequency the labelFrequency to set
	 */
	public void setLabelFrequency(String labelFrequency) {
		this.labelFrequency = labelFrequency;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HoomAssociation [uriAssociation=" + uriAssociation + ", uriSubject=" + uriSubject + ", uriObject="
				+ uriObject + ", uriFrequency=" + uriFrequency + ", labelFrequency=" + labelFrequency + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((labelFrequency == null) ? 0 : labelFrequency.hashCode());
		result = prime * result + ((uriAssociation == null) ? 0 : uriAssociation.hashCode());
		result = prime * result + ((uriFrequency == null) ? 0 : uriFrequency.hashCode());
		result = prime * result + ((uriObject == null) ? 0 : uriObject.hashCode());
		result = prime * result + ((uriSubject == null) ? 0 : uriSubject.hashCode());
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
		HoomAssociation other = (HoomAssociation) obj;
		if (labelFrequency == null) {
			if (other.labelFrequency != null)
				return false;
		} else if (!labelFrequency.equals(other.labelFrequency))
			return false;
		if (uriAssociation == null) {
			if (other.uriAssociation != null)
				return false;
		} else if (!uriAssociation.equals(other.uriAssociation))
			return false;
		if (uriFrequency == null) {
			if (other.uriFrequency != null)
				return false;
		} else if (!uriFrequency.equals(other.uriFrequency))
			return false;
		if (uriObject == null) {
			if (other.uriObject != null)
				return false;
		} else if (!uriObject.equals(other.uriObject))
			return false;
		if (uriSubject == null) {
			if (other.uriSubject != null)
				return false;
		} else if (!uriSubject.equals(other.uriSubject))
			return false;
		return true;
	}

	/**
	 * @return the uriInferredObject
	 */
	public Set<String> getUriInferredObject() {
		return uriInferredObject;
	}

	/**
	 * @param uriInferredObject the uriInferredObject to set
	 */
	public void setUriInferredObject(Set<String> uriInferredObject) {
		this.uriInferredObject = uriInferredObject;
	}
	
	/**
	 * @param uriInferredObject the uriInferredObject to set
	 */
	public void addUriInferredObject(String uriInferredObject) {
		this.uriInferredObject.add(uriInferredObject);
	}
	
	/**
	 * @param uriInferredObject the uriInferredObject to set
	 */
	public void addAllUriInferredObject(Set<String> uriInferredObject) {
		this.uriInferredObject.addAll(uriInferredObject);
	}

	/**
	 * @return the uriPereObject
	 */
	public Set<String> getUriPereObject() {
		return uriPereObject;
	}

	/**
	 * @param uriPereObject the uriPereObject to set
	 */
	public void setUriPereObject(Set<String> uriPereObject) {
		this.uriPereObject = uriPereObject;
	}
	
	/**
	 * @param uriInferredObject the uriInferredObject to set
	 */
	public void addUriPereObject(String uriPereObject) {
		this.uriPereObject.add(uriPereObject);
	}
	
	/**
	 * @param uriInferredObject the uriInferredObject to set
	 */
	public void addAllUriPereObject(Set<String> uriPereObject) {
		this.uriPereObject.addAll(uriPereObject);
	}
}
