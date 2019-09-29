package com.focowell.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.focowell.model.WorkflowNodeType;

public class WorkflowNodeExpandDto {
	private long nodeId;
	private String id;
	@Enumerated(EnumType.STRING)
	private WorkflowNodeType nodeType;
	private String label;
	private FormMasterExpandedDto formMaster;
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public WorkflowNodeType getNodeType() {
		return nodeType;
	}
	public void setNodeType(WorkflowNodeType nodeType) {
		this.nodeType = nodeType;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public FormMasterExpandedDto getFormMaster() {
		return formMaster;
	}
	public void setFormMaster(FormMasterExpandedDto formMaster) {
		this.formMaster = formMaster;
	}
	
	
}
