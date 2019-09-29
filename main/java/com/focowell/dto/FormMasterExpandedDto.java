package com.focowell.dto;

import java.util.Set;

import com.focowell.model.VirtualRowRecord;

public class FormMasterExpandedDto {
	private long id;
	private String formName;
	private String formDesc;
	private Set<FormDesignDto> formDesignList;
	private VirtualTableMasterDto virtualTableMaster;
//	private Set<FormRule> formRules;
//	private VirtualRowRecordDto virtualRowRecordsDto;
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
	public Set<FormDesignDto> getFormDesignList() {
		return formDesignList;
	}
	public void setFormDesignList(Set<FormDesignDto> formDesignList) {
		this.formDesignList = formDesignList;
	}
//	public Set<FormRule> getFormRules() {
//		return formRules;
//	}
//	public void setFormRules(Set<FormRule> formRules) {
//		this.formRules = formRules;
//	}
	
	
	public VirtualTableMasterDto getVirtualTableMaster() {
		return virtualTableMaster;
	}
	public void setVirtualTableMaster(VirtualTableMasterDto virtualTableMaster) {
		this.virtualTableMaster = virtualTableMaster;
	}
	
	
}
