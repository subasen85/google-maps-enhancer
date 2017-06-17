package com.traffigo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.traffigo.es.dto.AmbulanceDTO;
import com.traffigo.es.dto.StatusResponseDTO;
import com.traffigo.es.service.AmbulanceEsService;

@RestController
@RequestMapping(value = "/traffigo/api/general")
@CrossOrigin
public class GeneralAppController {
	
	private static final Logger LOG = LoggerFactory.getLogger(GeneralAppController.class);
	
	@Autowired
	AmbulanceEsService ambulanceEsService;

	@CrossOrigin
	@RequestMapping(value = "/trackcommonvehicle", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<String>  addAmbulance(
			@RequestBody AmbulanceDTO ambulanceDTO) {
		LOG.info("Inside getAllfavourites Start");
		StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
		statusResponseDTO.setStatus("failure");
		try {
			String isUpdated = ambulanceEsService.saveCommonVehicleInfo(ambulanceDTO);
			if("failure".equalsIgnoreCase(isUpdated)){
				statusResponseDTO.setMessage("Problem in tracking");
				return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.PARTIAL_CONTENT);
			}
			statusResponseDTO.setStatus("Success");
			statusResponseDTO.setMessage("common vehicle location tracked");
			return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Problem in allfavourite  : ", e);
			statusResponseDTO.setMessage("server problem");
			return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.PARTIAL_CONTENT);
		}
	}
	

}
