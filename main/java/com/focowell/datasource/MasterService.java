package com.focowell.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import com.focowell.model.dto.TenantDBDto;

public class MasterService {
	@Autowired
	private static Environment env;
	
//	public static String tenantDBRestUrl;
//	
//	@Autowired
//    public MasterService(@Value("${tenant.rest-url}") String tenantDBRestUrl) {
//        this.tenantDBRestUrl = tenantDBRestUrl;
//    }
	
//	@Value("${tenant.rest-url}")
//	public void setUserName(String url) {
//		MasterService.tenantDBRestUrl = url;
//	}
	
	public static List<TenantDBDto> getTenantDBList() {
		RestTemplate restTemplate=new RestTemplate();
		String tenantDBRestUrl=env.getProperty("tenant.rest-url");
		List<TenantDBDto> tenantDBList = restTemplate.getForObject(tenantDBRestUrl, List.class);
		return tenantDBList;
		
	}
	 public static Map<Object, Object> getDataSourceHashMap() {
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
