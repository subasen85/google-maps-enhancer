package com.traffigo.es.dto;

public class StatusResponseDTO {

	private String status;
	private String message;
	private String ambulanceUniqueId;
	private Boolean isOnLocation;
	
	
	
	public String getAmbulanceUniqueId() {
		return ambulanceUniqueId;
	}
	public void setAmbulanceUniqueId(String ambulanceUniqueId) {
		this.ambulanceUniqueId = ambulanceUniqueId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getIsOnLocation() {
		return isOnLocation;
	}
	public void setIsOnLocation(Boolean isOnLocation) {
		this.isOnLocation = isOnLocation;
	}
	
	
	
}
