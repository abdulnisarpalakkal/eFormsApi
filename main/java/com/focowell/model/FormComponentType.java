package com.focowell.model;

public enum FormComponentType {
	TEXT("TEXT"),
    RADIO("RADIO"),
    CHECKBOX("CHECKBOX"),
    COMPO("COMPO"),
    DATE("DATE"),
    LABEL("LABEL"),
	FILE("FILE"),
	IMG("IMG"),
	GRID("GRID");
   

    private final String componentType;
   
    private FormComponentType(String componentType){
        this.componentType = componentType;
        
    }
		
	public String getDataType() {
		return componentType;
	}

	public static FormComponentType parse(String componentType) {
		FormComponentType fieldComponentType = null; // Default
        for (FormComponentType item : FormComponentType.values()) {
            if (item.getDataType()==componentType) {
            	fieldComponentType = item;
                break;
            }
        }
        return fieldComponentType;
    }
}
