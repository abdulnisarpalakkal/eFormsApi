package com.focowell.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.focowell.model.dto.TenantDBDto;
import com.focowell.service.TenantDBService;

@Service(value="tenantDBService")
public class TenantDBServiceImpl implements TenantDBService {
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${tenant.rest-url}")
	private String tenantDBRestUrl;
	
	@Override
	public List<TenantDBDto> getTenantDBList() {
		ResponseEntity<TenantDBDto[]> response = restTemplate.exchange(tenantDBRestUrl,HttpMethod.GET,null, TenantDBDto[].class);
		List<TenantDBDto> tenantDBDtoList=Arrays.asList(response.getBody());
		return tenantDBDtoList;
		
	}
	 public Map<Object, Object> getDataSourceHashMap() {
		 	Map<Object,Object> hashMap = new HashMap<>();
		 	for(TenantDBDto tenantDB:getTenantDBList()) {
		 		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		        dataSource.setDriverClassName(tenantDB.getDriverName());
		        dataSource.setUrl(tenantDB.getUrl());
		        dataSource.setUsername(tenantDB.getUserName());
		        dataSource.setPassword(tenantDB.getPassword());
		        hashMap.put(tenantDB.getTenantId(), dataSource);
		 	}
	        
	        return hashMap;
	    }

}
