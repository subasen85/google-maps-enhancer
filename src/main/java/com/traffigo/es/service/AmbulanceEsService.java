package com.traffigo.es.service;

import com.traffigo.es.dto.AmbulanceDTO;

public interface AmbulanceEsService {
	
	public String saveAmulanceInfo(AmbulanceDTO ambulanceDTO);
	
	public String updateAmulanceInfo(AmbulanceDTO ambulanceDTO);
	
	public String saveCommonVehicleInfo(AmbulanceDTO ambulanceDTO);
	
	
}
