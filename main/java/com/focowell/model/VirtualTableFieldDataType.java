package com.focowell.model;



public enum VirtualTableFieldDataType {
	NUMBER(0),
    STRING(1),
    DATE(2),
    BOOLEAN(3);
   

    private final int dataType;
   
    private VirtualTableFieldDataType(int dataType){
        this.dataType = dataType;
        
    }
		
	public int getDataType() {
		return dataType;
	}

	public static VirtualTableFieldDataType parse(int dataType) {
		VirtualTableFieldDataType fieldDataType = null; // Default
        for (VirtualTableFieldDataType item : VirtualTableFieldDataType.values()) {
            if (item.getDataType()==dataType) {
            	fieldDataType = item;
                break;
            }
        }
        return fieldDataType;
    }

}
