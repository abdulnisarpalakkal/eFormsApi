package com.focowell.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.focowell.model.VirtualTableFieldDataType;

public class VirtualTableFieldDto {
	private long id;
	private String fieldName;
	@Enumerated(EnumType.ORDINAL)
    private VirtualTableFieldDataType fieldDataType;
	private String fieldDesc;
	private VirtualTableMasterDto virtualTableMaster;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public VirtualTableFieldDataType getFieldDataType() {
		return fieldDataType;
	}
	public void setFieldDataType(VirtualTableFieldDataType fieldDataType) {
		this.fieldDataType = fieldDataType;
	}
	public String getFieldDesc() {
		return fieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	public VirtualTableMasterDto getVirtualTableMaster() {
		return virtualTableMaster;
	}
	public void setVirtualTableMaster(VirtualTableMasterDto virtualTableMaster) {
		this.virtualTableMaster = virtualTableMaster;
	}
	
}
