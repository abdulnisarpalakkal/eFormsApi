package com.focowell.dto;

import java.util.Date;

public class WorkflowTrackDetDto {
	 private long id;
	 private Date accessDate;
	 private UserRefDto accessUser;
	 private WorkflowTrackMasterDto workflowTrackMaster;
	 private WorkflowTrackMasterDto childWorkflowTrackMaster;
	 private WorkflowNodeDto workflowActionNode;
	 private WorkflowNodeDto workflowFormNode;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getAccessDate() {
		return accessDate;
	}
	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}
	public UserRefDto getAccessUser() {
		return accessUser;
	}
	public void setAccessUser(UserRefDto accessUser) {
		this.accessUser = accessUser;
	}
	public WorkflowTrackMasterDto getWorkflowTrackMaster() {
		return workflowTrackMaster;
	}
	public void setWorkflowTrackMaster(WorkflowTrackMasterDto workflowTrackMaster) {
		this.workflowTrackMaster = workflowTrackMaster;
	}
	public WorkflowTrackMasterDto getChildWorkflowTrackMaster() {
		return childWorkflowTrackMaster;
	}
	public void setChildWorkflowTrackMaster(WorkflowTrackMasterDto childWorkflowTrackMaster) {
		this.childWorkflowTrackMaster = childWorkflowTrackMaster;
	}
	public WorkflowNodeDto getWorkflowActionNode() {
		return workflowActionNode;
	}
	public void setWorkflowActionNode(WorkflowNodeDto workflowActionNode) {
		this.workflowActionNode = workflowActionNode;
	}
	public WorkflowNodeDto getWorkflowFormNode() {
		return workflowFormNode;
	}
	public void setWorkflowFormNode(WorkflowNodeDto workflowFormNode) {
		this.workflowFormNode = workflowFormNode;
	}
	 
	 
}
