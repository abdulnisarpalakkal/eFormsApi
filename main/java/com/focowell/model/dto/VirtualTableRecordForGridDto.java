package com.focowell.model.dto;

import java.util.List;
import java.util.Map;

import com.focowell.model.VirtualTableField;

public class VirtualTableRecordForGridDto {
	List<Map> records;
	List<VirtualTableField> columns;
	public List<Map> getRecords() {
		return records;
	}
	public void setRecords(List<Map> records) {
		this.records = records;
	}
	public List<VirtualTableField> getColumns() {
		return columns;
	}
	public void setColumns(List<VirtualTableField> columns) {
		this.columns = columns;
	}
	
	
	
}
