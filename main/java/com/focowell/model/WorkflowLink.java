package com.focowell.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@NamedEntityGraph(
		  name = "link-entity-graph",
		  attributeNodes = {
		    @NamedAttributeNode("sourceNode"),
		    @NamedAttributeNode("targetNode"),
		    
		  }
		)
@Entity  
@Table(name="workflow_link")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkflowLink {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "workflow_link_seq")
    @SequenceGenerator(name = "workflow_link_seq",allocationSize = 1, sequenceName = "DB_WF_LINK_SEQ")
	@Column(name="id")
	private long linkId;
	
	@Column(name="LINK_LABEL")
	private String label;
	
	@JsonIgnoreProperties(value = {"sourceLinkList"}, allowSetters = true)
	@ManyToOne(fetch=FetchType.LAZY) 
	private WorkflowNode sourceNode;
	
	@JsonIgnoreProperties(value = {"targetLinkList"}, allowSetters = true)
	@ManyToOne(fetch=FetchType.LAZY) 
	private WorkflowNode targetNode;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	private WorkflowMaster workflowMaster;

	

	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public WorkflowNode getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(WorkflowNode sourceNode) {
		this.sourceNode = sourceNode;
	}

	public WorkflowNode getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(WorkflowNode targetNode) {
		this.targetNode = targetNode;
	}

	public WorkflowMaster getWorkflowMaster() {
		return workflowMaster;
	}

	public void setWorkflowMaster(WorkflowMaster workflowMaster) {
		this.workflowMaster = workflowMaster;
	}
	
	
	
	

}
