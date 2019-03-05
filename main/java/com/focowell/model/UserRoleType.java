package com.focowell.model;

public enum UserRoleType {
	 USER("USER"),
	    DBA("DBA"),
	    ADMIN("ADMIN");
	     
	    String userRoleType;
	    private UserRoleType(String userRoleType) {
	        this.userRoleType = userRoleType;
	    }
		public String getUserRoleType() {
			return userRoleType;
		}

	
	    
}
