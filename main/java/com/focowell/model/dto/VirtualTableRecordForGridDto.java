package com.focowell.model.dto;

import java.util.List;
import java.util.Map;

public class VirtualTableRecordForGridDto {
	List<Map> records;
	List<String> columns;
	public List<Map> getRecords() {
		return records;
	}
	public void setRecords(List<Map> records) {
		this.records = records;
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
	
}
