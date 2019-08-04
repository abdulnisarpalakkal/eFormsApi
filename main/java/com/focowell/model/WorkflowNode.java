package com.focowell.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity  
@Table(name="workflow_node")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkflowNode {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "workflow_node_seq")
    @SequenceGenerator(name = "workflow_node_seq",allocationSize = 1, sequenceName = "DB_WF_NODE_SEQ")
	@Column(name="id")
	private long nodeId;
	
	@Transient
	@JsonSerialize
	@JsonDeserialize
	private String id;
	
	@Transient
	private String nodeLinkId;
	
	@Column(name="NODE_TYPE")
	@Enumerated(EnumType.STRING)
	private WorkflowNodeType nodeType;
	
	@Column(name="NODE_LABEL")
	private String label;
	
	
	@JsonIgnoreProperties(value="workflowNodeList",allowSetters=true) 
	@ManyToOne(fetch=FetchType.LAZY)  
	private FormMaster formMaster;
	
	@JsonIgnoreProperties(value="childNodeList",allowSetters=true) 
	@ManyToOne(fetch=FetchType.LAZY) 
	private WorkflowMaster childWorkflow;
	
	@JsonIgnoreProperties(value="workflowNodeList",allowSetters=true) 
	@ManyToOne(fetch=FetchType.LAZY) 
	private WorkflowMaster workflowMaster;
	
	
	@JsonIgnore
	@OneToMany( mappedBy="sourceNode",fetch=FetchType.LAZY,cascade= CascadeType.ALL)   
	public Set<WorkflowLink> sourceLinkList;
	
	@JsonIgnore
	@OneToMany( mappedBy="targetNode",fetch=FetchType.LAZY,cascade= CascadeType.ALL)   
	public Set<WorkflowLink> targetLinkList;
	
//	@JsonManagedReference
	@JsonIgnoreProperties(value="workflowNode",allowSetters=true)
	@OneToMany(mappedBy="workflowNode",fetch=FetchType.LAZY,cascade= CascadeType.ALL,orphanRemoval=true)
	public Set<ActionEventObject> actionEventObjects;

	@JsonIgnoreProperties(value= {"workflowActionNode"},allowSetters=true)
	@OneToMany(mappedBy="workflowActionNode",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	public Set<WorkflowTrackDet> workflowActionTrackDetList;
	
	
		
	@JsonIgnoreProperties(value= {"workflowFormNode"},allowSetters=true)
	@OneToMany(mappedBy="workflowFormNode",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	public Set<WorkflowTrackDet> workflowFormTrackDetList;

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeLinkId() {
		return nodeLinkId;
	}

	public void setNodeLinkId(String nodeLinkId) {
		this.nodeLinkId = nodeLinkId;
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

	public FormMaster getFormMaster() {
		return formMaster;
	}

	public void setFormMaster(FormMaster formMaster) {
		this.formMaster = formMaster;
	}

	
	public Set<ActionEventObject> getActionEventObjects() {
		return actionEventObjects;
	}

	public void setActionEventObjects(Set<ActionEventObject> actionEventObjects) {
		this.actionEventObjects = actionEventObjects;
	}

	public WorkflowMaster getWorkflowMaster() {
		return workflowMaster;
	}

	public void setWorkflowMaster(WorkflowMaster workflowMaster) {
		this.workflowMaster = workflowMaster;
	}

	public Set<WorkflowLink> getSourceLinkList() {
		return sourceLinkList;
	}

	public void setSourceLinkList(Set<WorkflowLink> sourceLinkList) {
		this.sourceLinkList = sourceLinkList;
	}

	public Set<WorkflowLink> getTargetLinkList() {
		return targetLinkList;
	}

	public void setTargetLinkList(Set<WorkflowLink> targetLinkList) {
		this.targetLinkList = targetLinkList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WorkflowMaster getChildWorkflow() {
		return childWorkflow;
	}

	public void setChildWorkflow(WorkflowMaster childWorkflow) {
		this.childWorkflow = childWorkflow;
	}

	public Set<WorkflowTrackDet> getWorkflowActionTrackDetList() {
		return workflowActionTrackDetList;
	}

	public void setWorkflowActionTrackDetList(Set<WorkflowTrackDet> workflowActionTrackDetList) {
		this.workflowActionTrackDetList = workflowActionTrackDetList;
	}

	public Set<WorkflowTrackDet> getWorkflowFormTrackDetList() {
		return workflowFormTrackDetList;
	}

	public void setWorkflowFormTrackDetList(Set<WorkflowTrackDet> workflowFormTrackDetList) {
		this.workflowFormTrackDetList = workflowFormTrackDetList;
	}

	


	

	
	
	
	
	
}
