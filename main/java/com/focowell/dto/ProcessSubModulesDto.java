package com.focowell.dto;

import java.util.List;

import com.focowell.model.FormMaster;
import com.focowell.model.VirtualTableMaster;
import com.focowell.model.WorkflowMaster;

public class ProcessSubModulesDto {
	private List<VirtualTableMaster> virtualTableMasters;
	private List<FormMaster> formMasters;
	private List<WorkflowMaster> workflowMasters;
	public List<VirtualTableMaster> getVirtualTableMasters() {
		return virtualTableMasters;
	}
	public void setVirtualTableMasters(List<VirtualTableMaster> virtualTableMasters) {
		this.virtualTableMasters = virtualTableMasters;
	}
	public List<FormMaster> getFormMasters() {
		return formMasters;
	}
	public void setFormMasters(List<FormMaster> formMasters) {
		this.formMasters = formMasters;
	}
	public List<WorkflowMaster> getWorkflowMasters() {
		return workflowMasters;
	}
	public void setWorkflowMasters(List<WorkflowMaster> workflowMasters) {
		this.workflowMasters = workflowMasters;
	}
	
	
	
}
