package com.focowell.dto;

import java.util.List;

public class VirtualRowRecordDto {
	private String id;
	private List<VirtualTableRecordsDto> records;
	private long pkValue;
	private VirtualTableMasterDto virtualTableMaster;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<VirtualTableRecordsDto> getRecords() {
		return records;
	}
	public void setRecords(List<VirtualTableRecordsDto> records) {
		this.records = records;
	}
	public long getPkValue() {
		return pkValue;
	}
	public void setPkValue(long pkValue) {
		this.pkValue = pkValue;
	}
	public VirtualTableMasterDto getVirtualTableMaster() {
		return virtualTableMaster;
	}
	public void setVirtualTableMaster(VirtualTableMasterDto virtualTableMaster) {
		this.virtualTableMaster = virtualTableMaster;
	}
}
