package com.focowell.model.dto;

import java.io.Serializable;
import java.util.List;

import com.focowell.model.VirtualTableMaster;
import com.focowell.model.VirtualTableRecords;



public class VirtualRowRecordsDto implements Serializable {
	public VirtualRowRecordsDto() {
		
	}
	public VirtualRowRecordsDto(List<VirtualTableRecords> records,long pkValue,VirtualTableMaster virtualTableMaster) {
		this.records=records;
		this.pkValue=pkValue;
		this.virtualTableMaster=virtualTableMaster;
	}
	private String id;
	private List<VirtualTableRecords> records;
	private long pkValue;
	private VirtualTableMaster virtualTableMaster;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<VirtualTableRecords> getRecords() {
		return records;
	}
	public void setRecords(List<VirtualTableRecords> records) {
		this.records = records;
	}
	public long getPkValue() {
		return pkValue;
	}
	public void setPkValue(long pkValue) {
		this.pkValue = pkValue;
	}
	public VirtualTableMaster getVirtualTableMaster() {
		return virtualTableMaster;
	}
	public void setVirtualTableMaster(VirtualTableMaster virtualTableMaster) {
		this.virtualTableMaster = virtualTableMaster;
	}
	
}
