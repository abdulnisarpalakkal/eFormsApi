package com.focowell.dto;

import com.focowell.model.VirtualTableField;

public class VirtualTableRecordsDto {
	private long id;
	private String stringValue;
	private VirtualTableFieldDto virtualTableFields;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public VirtualTableFieldDto getVirtualTableFields() {
		return virtualTableFields;
	}
	public void setVirtualTableFields(VirtualTableFieldDto virtualTableFields) {
		this.virtualTableFields = virtualTableFields;
	}
	
}
