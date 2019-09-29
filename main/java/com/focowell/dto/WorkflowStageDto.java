package com.focowell.dto;

import java.util.List;
import java.util.Set;

import com.focowell.model.WorkflowNode;

public class WorkflowStageDto {
	private WorkflowNodeDto formNode;
	private Set<WorkflowNodeDto> actionNodes;
	private WorkflowMasterDto workflowMaster;
	private WorkflowTrackDetDto workflowTrackDet;
	private WorkflowNodeDto selectedActionNode;
	private List<String> breadCrumpList;
	public WorkflowNodeDto getFormNode() {
		return formNode;
	}
	public void setFormNode(WorkflowNodeDto formNode) {
		this.formNode = formNode;
	}
	public Set<WorkflowNodeDto> getActionNodes() {
		return actionNodes;
	}
	public void setActionNodes(Set<WorkflowNodeDto> actionNodes) {
		this.actionNodes = actionNodes;
	}
	public WorkflowMasterDto getWorkflowMaster() {
		return workflowMaster;
	}
	public void setWorkflowMaster(WorkflowMasterDto workflowMaster) {
		this.workflowMaster = workflowMaster;
	}
	public WorkflowTrackDetDto getWorkflowTrackDet() {
		return workflowTrackDet;
	}
	public void setWorkflowTrackDet(WorkflowTrackDetDto workflowTrackDet) {
		this.workflowTrackDet = workflowTrackDet;
	}
	public List<String> getBreadCrumpList() {
		return breadCrumpList;
	}
	public void setBreadCrumpList(List<String> breadCrumpList) {
		this.breadCrumpList = breadCrumpList;
	}
	public WorkflowNodeDto getSelectedActionNode() {
		return selectedActionNode;
	}
	public void setSelectedActionNode(WorkflowNodeDto selectedActionNode) {
		this.selectedActionNode = selectedActionNode;
	}

	

	
	
}
