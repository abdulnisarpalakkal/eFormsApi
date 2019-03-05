package com.focowell.model;

public enum VirtualTableConstraintType {
	PRIMARY_KEY("PRIMARY_KEY"),
    FOREIGN_KEY("FOREIGN_KEY"),
    NOT_NULL("NOT_NULL");
     
    String constraintType;
    private VirtualTableConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }
	public String getConstraintType() {
		return constraintType;
	}

	
}
