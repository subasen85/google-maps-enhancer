package com.traffigo.es.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import com.traffigo.es.model.AmbulanceESInfo;
import com.traffigo.es.model.CommonVehicleESInfo;

public interface CommonVehicleRespository extends ElasticsearchRepository<CommonVehicleESInfo, Integer> {
//	public List<AmbulanceESInfo> findByProductNameIgnoreCase(String productName);
	//public List<ProductESInfo> findByPriceBetween(double beginning,double end);
	//@Query("select es from ProductESInfo es where lower(es.productName) = ?1 and es.mobileNo =?2")
//	public AmbulanceESInfo findByProductNameIgnoreCaseAndMobileNo(String productName,String mobileNo);
	
	public CommonVehicleESInfo findByAppId(String appId);
	
	public List<CommonVehicleESInfo> findAllByLatitudeBetweenAndLongitudeBetween(
			String minLat, String maxLat, String minLon, String maxLon);
	
}
