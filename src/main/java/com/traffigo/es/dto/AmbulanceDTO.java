package com.traffigo.es.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AmbulanceDTO {

	private String ambulanceName;
	private Double latitude;
	private Double longitude;
	private String sourceLocation;
	private String destinationLocation;
	private String appId;
	private String ambulanceUniqueId;
	private Boolean isDestinationReached;
	
	public Boolean getIsDestinationReached() {
		return isDestinationReached;
	}
	public void setIsDestinationReached(Boolean isDestinationReached) {
		this.isDestinationReached = isDestinationReached;
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
	public String getAmbulanceName() {
		return ambulanceName;
	}
	public void setAmbulanceName(String ambulanceName) {
		this.ambulanceName = ambulanceName;
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
	
	
}
