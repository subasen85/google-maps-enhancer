package com.traffigo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.traffigo.es.dto.AmbulanceDTO;
import com.traffigo.es.dto.StatusResponseDTO;
import com.traffigo.es.service.AmbulanceEsService;

@RestController
@RequestMapping(value = "/traffigo/api/ambulance")
@CrossOrigin
public class AmbulanceController {

	private static final Logger LOG = LoggerFactory.getLogger(AmbulanceController.class);
	
	@Autowired
	AmbulanceEsService ambulanceEsService;

	@CrossOrigin
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<String>  addAmbulance(
			@RequestBody AmbulanceDTO ambulanceDTO) {
		LOG.info("Inside getAllfavourites Start");
		StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
		statusResponseDTO.setStatus("failure");
		try {
			String ambulanceUniqueId = ambulanceEsService.saveAmulanceInfo(ambulanceDTO);
			if("failure".equalsIgnoreCase(ambulanceUniqueId)){
				statusResponseDTO.setMessage("Problem in saving");
				return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.PARTIAL_CONTENT);
			}
			statusResponseDTO.setStatus("success");
			statusResponseDTO.setMessage("Ambulance saved");
			statusResponseDTO.setAmbulanceUniqueId(ambulanceUniqueId);
			return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Problem in allfavourite  : ", e);
			
			statusResponseDTO.setMessage("server problem");
			return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.PARTIAL_CONTENT);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "/ongoing", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<String>  updateAmbulanceLocation(
			@RequestBody AmbulanceDTO ambulanceDTO) {
		LOG.info("Inside getAllfavourites Start");
		StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
		statusResponseDTO.setStatus("failure");
		try {
			String isUpdated = ambulanceEsService.updateAmulanceInfo(ambulanceDTO);
			if("failure".equalsIgnoreCase(isUpdated)){
				statusResponseDTO.setMessage("Problem in updating");
				return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.PARTIAL_CONTENT);
			}
			statusResponseDTO.setStatus("success");
			statusResponseDTO.setMessage("Ambulance location updated");
			return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Problem in allfavourite  : ", e);
			
			statusResponseDTO.setMessage("server problem");
			return new ResponseEntity<String>(new Gson().toJson(statusResponseDTO), HttpStatus.PARTIAL_CONTENT);
		}
	}
	

}
