package com.focowell.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.focowell.model.deserializer.WorkflowStageDeserializer;

//@JsonDeserialize(using = WorkflowStageDeserializer.class)
public class WorkflowStage implements Serializable {
	
	public WorkflowStage() {
		
	}
	public WorkflowStage(long id,WorkflowNode formNode,Set<WorkflowNode> actionNodes, WorkflowMaster workflowMaster,WorkflowTrackDet workflowTrackDet,WorkflowNode selectedActionNode) {
		this.id=id;
		this.formNode=formNode;
		this.actionNodes=actionNodes;
		this.workflowMaster=workflowMaster;
		this.workflowTrackDet=workflowTrackDet;
		this.selectedActionNode=selectedActionNode;
		
	}
	private long id;
	private WorkflowNode formNode;
	private Set<WorkflowNode> actionNodes;
	private WorkflowMaster workflowMaster;
	private WorkflowTrackDet workflowTrackDet;
	
	private WorkflowNode selectedActionNode;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public WorkflowNode getFormNode() {
		return formNode;
	}
	public void setFormNode(WorkflowNode formNode) {
		this.formNode = formNode;
	}
	public Set<WorkflowNode> getActionNodes() {
		return actionNodes;
	}
	public void setActionNodes(Set<WorkflowNode> actionNodes) {
		this.actionNodes = actionNodes;
	}
	public WorkflowMaster getWorkflowMaster() {
		return workflowMaster;
	}
	public void setWorkflowMaster(WorkflowMaster workflowMaster) {
		this.workflowMaster = workflowMaster;
	}
	
	
	
	public WorkflowTrackDet getWorkflowTrackDet() {
		return workflowTrackDet;
	}
	public void setWorkflowTrackDet(WorkflowTrackDet workflowTrackDet) {
		this.workflowTrackDet = workflowTrackDet;
	}
	public WorkflowNode getSelectedActionNode() {
		return selectedActionNode;
	}
	public void setSelectedActionNode(WorkflowNode selectedActionNode) {
		this.selectedActionNode = selectedActionNode;
	}
	
	

}
