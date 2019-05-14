package com.focowell.service;

import java.util.List;
import java.util.Map;

import com.focowell.model.dto.TenantDBDto;

public interface TenantDBService {
	List<TenantDBDto> getTenantDBList();
	Map<Object, Object> getDataSourceHashMap();

}
