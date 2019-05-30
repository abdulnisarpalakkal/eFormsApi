package com.focowell.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.focowell.model.Constants;

public class CustomRoutingDataSource extends AbstractRoutingDataSource  {

	
	@Value("${tenant.default}")
	private String defaultTenant;
	
	@Override
	protected Object determineCurrentLookupKey() {

		    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();     // get request object
	        if(attr!=null) {
	        	Object obj=attr.getAttribute(Constants.TENANT_ID, RequestAttributes.SCOPE_REQUEST).toString();       // find parameter from request
	            return obj;
	           
	        }else{
	            return defaultTenant;             // default data source
	        }
	}

}
