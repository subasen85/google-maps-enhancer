package com.traffigo.es.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import com.traffigo.es.model.AmbulanceESInfo;

public interface AmbulanceRespository extends ElasticsearchRepository<AmbulanceESInfo, Integer> {
//	public List<AmbulanceESInfo> findByProductNameIgnoreCase(String productName);
	//public List<ProductESInfo> findByPriceBetween(double beginning,double end);
	//@Query("select es from ProductESInfo es where lower(es.productName) = ?1 and es.mobileNo =?2")
//	public AmbulanceESInfo findByProductNameIgnoreCaseAndMobileNo(String productName,String mobileNo);
	
	public AmbulanceESInfo findByAmbulanceUniqueId(String ambulanceUniqueId);
	
}
