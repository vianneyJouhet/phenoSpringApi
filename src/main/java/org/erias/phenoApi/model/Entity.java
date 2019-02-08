package org.erias.phenoApi.model;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName= "entity")
public class Entity {
	
	@Id
	private String id;
	
	@Field(type = FieldType.Attachment)
	private String uri;
	
	@Field(type = FieldType.Text)
	private String prefLabel;
	
	@Field(type = FieldType.Nested)
	private Set<Entitylabel> labels;
	
	public Entity (EntityInGraph entityInGraph) {
		try {
			this.setId(entityInGraph.getUri());
			this.uri=entityInGraph.getUri();
			this.labels = entityInGraph.getLabels();
			this.prefLabel=entityInGraph.getPrefLabel();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @throws NoSuchAlgorithmException 
	 */
	public void setId(String uri) throws NoSuchAlgorithmException {
		this.id = DatatypeConverter
			      .printHexBinary(MessageDigest.getInstance("MD5").digest(uri.getBytes()))
			      .toUpperCase();
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the prefLabel
	 */
	public String getPrefLabel() {
		return prefLabel;
	}

	/**
	 * @param prefLabel the prefLabel to set
	 */
	public void setPrefLabel(String prefLabel) {
		this.prefLabel = prefLabel;
	}

	/**
	 * @return the labels
	 */
	public Set<Entitylabel> getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(Set<Entitylabel> labels) {
		this.labels = labels;
	}

	

	
}
