package com.focowell.model;

import java.io.Serializable;
import java.util.List;



public class VirtualRowRecord implements Serializable {
	public VirtualRowRecord() {
		
	}
	public VirtualRowRecord(List<VirtualTableRecords> records,long pkValue,VirtualTableMaster virtualTableMaster) {
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
	@Override
	public String toString() {
		return "VirtualRowRecordsDto [id=" + id + ", records=" + records + ", pkValue=" + pkValue
				+ ", virtualTableMaster=" + virtualTableMaster + "]";
	}
	
}
