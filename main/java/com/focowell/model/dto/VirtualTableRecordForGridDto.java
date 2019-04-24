package com.focowell.model.dto;

import java.util.List;
import java.util.Map;

import com.focowell.model.FormDesign;
import com.focowell.model.VirtualTableField;

public class VirtualTableRecordForGridDto {
	List<Map> records;
	List<VirtualTableField> columns;
	List<FormDesign> formDesigns;
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
	public List<FormDesign> getFormDesigns() {
		return formDesigns;
	}
	public void setFormDesigns(List<FormDesign> formDesigns) {
		this.formDesigns = formDesigns;
	}
	
	
	
	
}
