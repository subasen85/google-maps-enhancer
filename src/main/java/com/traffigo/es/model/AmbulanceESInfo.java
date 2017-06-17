package com.traffigo.es.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(indexName = "es-search-traffigo", type = "ambulanceinfo")
public class AmbulanceESInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String ambulanceName;
	private String sourceLocation;
	private String destinationLocation;
	private Double latitude;
	private Double longitude;
	private String ambulanceUniqueId;
	private String appId;
	private Boolean isDestinationReached;
	
	
	
	public Boolean getIsDestinationReached() {
		return isDestinationReached;
	}
	public void setIsDestinationReached(Boolean isDestinationReached) {
		this.isDestinationReached = isDestinationReached;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAmbulanceName() {
		return ambulanceName;
	}
	public void setAmbulanceName(String ambulanceName) {
		this.ambulanceName = ambulanceName;
	}
	public String getSourceLocation() {
		return sourceLocation;
	}
	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public String getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public String getAmbulanceUniqueId() {
		return ambulanceUniqueId;
	}
	public void setAmbulanceUniqueId(String ambulanceUniqueId) {
		this.ambulanceUniqueId = ambulanceUniqueId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "AmbulanceESInfo [id=" + id + ", ambulanceName=" + ambulanceName + ", sourceLocation=" + sourceLocation
				+ ", destinationLocation=" + destinationLocation + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", amublanceUniqueId=" + ambulanceUniqueId + ", appId=" + appId + "]";
	}
	
	
}