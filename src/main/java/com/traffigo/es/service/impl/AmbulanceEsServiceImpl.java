package com.traffigo.es.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traffigo.es.dto.AmbulanceDTO;
import com.traffigo.es.model.AmbulanceESInfo;
import com.traffigo.es.model.CommonVehicleESInfo;
import com.traffigo.es.repository.AmbulanceRespository;
import com.traffigo.es.repository.CommonVehicleRespository;
import com.traffigo.es.service.AmbulanceEsService;

@Service
public class AmbulanceEsServiceImpl implements AmbulanceEsService {
	static final Logger LOG = LoggerFactory.getLogger(AmbulanceEsServiceImpl.class);

	@Autowired
	AmbulanceRespository ambulanceRespository;

	@Autowired
	CommonVehicleRespository commonVehicleRespository;

	@Override
	public String saveAmulanceInfo(AmbulanceDTO ambulanceDTO) {

		AmbulanceESInfo ambulanceESInfo = new AmbulanceESInfo();
		String ambulanceUniqueId = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
		Integer id= Integer.parseInt(RandomStringUtils.randomNumeric(6));
		ambulanceESInfo.setId(id);
		ambulanceESInfo.setAmbulanceName(ambulanceDTO.getAmbulanceName());
		ambulanceESInfo.setAmbulanceUniqueId(ambulanceUniqueId);
		ambulanceESInfo.setAppId(ambulanceDTO.getAppId());
		ambulanceESInfo.setDestinationLocation(ambulanceDTO.getDestinationLocation());
		ambulanceESInfo.setLatitude(ambulanceDTO.getLatitude());
		ambulanceESInfo.setLongitude(ambulanceDTO.getLongitude());
		ambulanceESInfo.setSourceLocation(ambulanceDTO.getSourceLocation());
		ambulanceESInfo.setIsDestinationReached(false);

		ambulanceESInfo = ambulanceRespository.save(ambulanceESInfo);
		if (ambulanceESInfo != null) {
			return ambulanceUniqueId;
		} else {
			return "failure";
		}

	}

	@Override
	public String updateAmulanceInfo(AmbulanceDTO ambulanceDTO) {
		// TODO Auto-generated method stub

		AmbulanceESInfo ambulanceESInfo = ambulanceRespository
				.findByAmbulanceUniqueId(ambulanceDTO.getAmbulanceUniqueId());
		if (ambulanceESInfo != null) {
			ambulanceESInfo.setLatitude(ambulanceDTO.getLatitude());
			ambulanceESInfo.setLongitude(ambulanceDTO.getLongitude());
			ambulanceESInfo.setIsDestinationReached(ambulanceDTO.getIsDestinationReached());
			ambulanceESInfo = ambulanceRespository.save(ambulanceESInfo);
			if (ambulanceESInfo != null) {

				// Find vehicles to send notifications
				List<CommonVehicleESInfo> commonVehicleESInfos = findNearestVehicles(ambulanceDTO);
				if (commonVehicleESInfos != null && commonVehicleESInfos.size() > 0) {
					
					LOG.info("commonVehicleESInfos : "+commonVehicleESInfos.size());
					// Send push notifcations
					for (CommonVehicleESInfo commonVehicleESInfo : commonVehicleESInfos) {
						sendPushNotifcation(commonVehicleESInfo.getAppId(),ambulanceDTO.getLatitude(),ambulanceDTO.getLongitude());
					}
				}
				return "success";
			} else {
				return "failure";
			}

		}

		return "failure";
	}

	private void sendPushNotifcation(String appId,Double amublanceLatitude,Double ambulanceLongitude) {
		String server_key = "AAAAoL5kIOs:APA91bHusnvoXWsMFQJXzFh84Xqjv9xAU4oyzwDf4230HOWF-HomrYbUGooh1cQ5hY1NmylkHFbDPxr5uZBJmuInwmo1gbTaZlgJalaJznRCOzBs0doZY4uD0TywUhYJjjVRcvsCIjtJ";
		String FCM_URL = "https://fcm.googleapis.com/fcm/send";
		try {

			URL url = new URL(FCM_URL);

			// create connection.

			HttpURLConnection conn;

			conn = (HttpURLConnection) url.openConnection();

			conn.setUseCaches(false);

			conn.setDoInput(true);

			conn.setDoOutput(true);

			// set method as POST or GET

			conn.setRequestMethod("POST");

			// LOG.info("FCM.send_FCM_Notification()... Siva checking
			// RequestMethod " + conn.getRequestMethod());

			// pass FCM server key

			conn.setRequestProperty("Authorization", "key=" + server_key);

			// Specify Message Format

			conn.setRequestProperty("Content-Type", "application/json");

			// Create JSON Object & pass value

			JSONObject infoJson = new JSONObject();

			// infoJson.put("title", "Here is your notification.");
			infoJson.put("title", "GeneralApp");

			infoJson.put("body", "Ambulance very near to u. Pls allow ambulance to go");
			
			infoJson.put("amublance_latitude", amublanceLatitude);
			
			infoJson.put("ambulance_longitude", ambulanceLongitude);

			infoJson.put("sound", "default");

			infoJson.put("priority", "high");

			JSONObject json = new JSONObject();

			json.put("to", appId.trim());

			json.put("data", infoJson);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

			wr.write(json.toString());

			wr.flush();

			int status = 0;

			if (null != conn) {

				status = conn.getResponseCode();

			}

			if (status != 0) {

				LOG.info("FCM.send_FCM_Notification()... Siva checking status " + status);
				if (status == 200) {

					// SUCCESS message

					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

					LOG.info("Android Notification Response : " + reader.readLine());

				} else if (status == 401) {

					// client side error

					LOG.info("Notification Response : TokenId : " + appId + " Error occurred :");

				} else if (status == 501) {

					// server side error

					LOG.info("Notification Response : [ errorCode=ServerError ] TokenId : " + appId);

				} else if (status == 503) {

					// server side error

					LOG.info("Notification Response : FCM Service is Unavailable  TokenId : " + appId);

				}

			}

		} catch (MalformedURLException mlfexception) {

			// Prototcal Error

			LOG.info("Error occurred while sending push Notification!.." + mlfexception.getMessage());

		} catch (IOException mlfexception) {

			// URL problem

			LOG.info("Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());

		} catch (JSONException jsonexception) {

			// Message format error

			LOG.info("Message Format, Error occurred while sending push Notification!.." + jsonexception.getMessage());

		} catch (Exception exception) {

			// General Error or exception.

			LOG.info("Error occurred while sending push Notification!.." + exception.getMessage());

		}

	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	private List<CommonVehicleESInfo> findNearestVehicles(AmbulanceDTO ambulanceDTO) {
		final Double distance = 111.045;// Earth standard multiplication factor
		// for Kilometer Calculation
		Double radius = 0.5;

		Double myLat = ambulanceDTO.getLatitude();
		Double myLon = ambulanceDTO.getLongitude();
		Double lat1 = myLat - (radius / distance);
		Double lat2 = myLat + (radius / distance);
		Double lan1 = myLon - (radius / (distance * Math.cos(deg2rad(myLat))));
		Double lan2 = myLon + (radius / (distance * Math.cos(deg2rad(myLat))));
		Double minLat = lat1;
		Double maxLat = lat2;
		Double minLon = lan1;
		Double maxLon = lan2;
		if (myLat < 0) {
			minLat = lat2;
			maxLat = lat1;
		}
		if (myLon < 0) {
			minLon = lan2;
			maxLon = lan1;
		}
		LOG.info("myLat=" + myLat + "; myLon=" + myLon);
		LOG.info("minLat=" + minLat + "; maxLat=" + maxLat);

		List<CommonVehicleESInfo> commonVehicleESInfos = commonVehicleRespository
				.findAllByLatitudeBetweenAndLongitudeBetween(minLat.toString(), maxLat.toString(), minLon.toString(),
						maxLon.toString());

		LOG.info("Nearest vehicles commonVehicleESInfos : " + commonVehicleESInfos);
		
		return commonVehicleESInfos;
	}

	@Override
	public String saveCommonVehicleInfo(AmbulanceDTO ambulanceDTO) {
		CommonVehicleESInfo commonVehicleESInfo = commonVehicleRespository.findByAppId(ambulanceDTO.getAppId());

		if (commonVehicleESInfo == null) {
			commonVehicleESInfo = new CommonVehicleESInfo();
			Integer id= Integer.parseInt(RandomStringUtils.randomNumeric(6));
			commonVehicleESInfo.setId(id);
			commonVehicleESInfo.setAppId(ambulanceDTO.getAppId());
		}
		commonVehicleESInfo.setLatitude(ambulanceDTO.getLatitude());
		commonVehicleESInfo.setLongitude(ambulanceDTO.getLongitude());

		commonVehicleESInfo = commonVehicleRespository.save(commonVehicleESInfo);
		if (commonVehicleESInfo != null) {
			return "success";
		} else {
			return "failure";
		}
	}

}
