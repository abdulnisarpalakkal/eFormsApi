package com.focowell.dto;

import java.util.Set;

import com.focowell.model.FormDesign;
import com.focowell.model.FormRule;
import com.focowell.model.VirtualRowRecord;

public class FormMasterDto {

	private long id;
	private String formName;
	private String formDesc;

	private VirtualRowRecord virtualRowRecordsDto;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormDesc() {
		return formDesc;
	}
	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	public VirtualRowRecord getVirtualRowRecordsDto() {
		return virtualRowRecordsDto;
	}
	public void setVirtualRowRecordsDto(VirtualRowRecord virtualRowRecordsDto) {
		this.virtualRowRecordsDto = virtualRowRecordsDto;
	}
	
}
