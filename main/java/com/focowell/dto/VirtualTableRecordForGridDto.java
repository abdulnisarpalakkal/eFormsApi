package com.focowell.dto;

import java.util.List;
import java.util.Map;

import com.focowell.model.FormDesign;
import com.focowell.model.VirtualRowRecord;
import com.focowell.model.VirtualTableField;

public class VirtualTableRecordForGridDto {
	List<VirtualRowRecord> rowRecords;
	List<VirtualTableField> columns;
	List<FormDesign> formDesigns;
	
	public List<VirtualRowRecord> getRowRecords() {
		return rowRecords;
	}
	public void setRowRecords(List<VirtualRowRecord> rowRecords) {
		this.rowRecords = rowRecords;
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
