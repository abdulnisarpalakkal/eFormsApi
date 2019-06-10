package com.focowell.model;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name="workflow_master")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkflowMaster {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "workflow_master_seq")
    @SequenceGenerator(name = "workflow_master_seq",allocationSize = 1, sequenceName = "DB_WF_MASTER_SEQ")
    private long id;
	
	@NotEmpty
	@Column(name="WORKFLOW_NAME", nullable=false)
	@Size(min=2)
    private String workflowName;
	
	@Column(name="WORKFLOW_DESC")
    private String workflowDesc;
	
	@Column(name="PUBLISHED")
    private boolean published;
	
	@Column(name="PUBLISH_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date publishDate=new Date();
	
	@Column(name="child")
	private boolean child;
	
	@ManyToOne(fetch=FetchType.LAZY)    
	private User publishUser;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	private ProcessData process;
	

	@JsonIgnoreProperties(value= "workflowMaster",allowSetters=true)
	@OneToMany( mappedBy="workflowMaster",cascade = CascadeType.ALL,orphanRemoval=true)   
	public Set<WorkflowNode> workflowNodeList;
	
	@OneToMany( mappedBy="workflowMaster",cascade = CascadeType.ALL,orphanRemoval=true)   
	public Set<WorkflowLink> workflowLinkList;

	@JsonIgnoreProperties(value= "workflowMaster",allowSetters=true)
//	@JsonIgnore
	@OneToMany( mappedBy="workflowMaster",cascade = CascadeType.REMOVE,orphanRemoval=true)   
	public Set<WorkflowTrackMaster> workflowTrackList;
	
	@JsonIgnoreProperties(value= "childWorkflow",allowSetters=true)
	@OneToMany( mappedBy="childWorkflow")   
	public Set<WorkflowNode> childNodeList;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getWorkflowDesc() {
		return workflowDesc;
	}

	public void setWorkflowDesc(String workflowDesc) {
		this.workflowDesc = workflowDesc;
	}

	public ProcessData getProcess() {
		return process;
	}

	public void setProcess(ProcessData process) {
		this.process = process;
	}

	public Set<WorkflowNode> getWorkflowNodeList() {
		return workflowNodeList;
	}

	public void setWorkflowNodeList(Set<WorkflowNode> workflowNodeList) {
		this.workflowNodeList = workflowNodeList;
	}

	public Set<WorkflowLink> getWorkflowLinkList() {
		return workflowLinkList;
	}

	public void setWorkflowLinkList(Set<WorkflowLink> workflowLinkList) {
		this.workflowLinkList = workflowLinkList;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public User getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(User publishUser) {
		this.publishUser = publishUser;
	}

	public boolean isChild() {
		return child;
	}

	public void setChild(boolean child) {
		this.child = child;
	}

	public Set<WorkflowTrackMaster> getWorkflowTrackList() {
		return workflowTrackList;
	}

	public void setWorkflowTrackList(Set<WorkflowTrackMaster> workflowTrackList) {
		this.workflowTrackList=workflowTrackList;
//		if(workflowTrackList==null) {
//			this.workflowTrackList=Collections.emptySet();
//		}
//		else {
//			
//		
//			this.workflowTrackList.clear();
//		    if (workflowTrackList != null) {
//		        this.workflowTrackList.addAll(workflowTrackList);
//		    }
//		}
		
	}

	public Set<WorkflowNode> getChildNodeList() {
		return childNodeList;
	}

	public void setChildNodeList(Set<WorkflowNode> childNodeList) {
		this.childNodeList = childNodeList;
	}

	@Override
	public String toString() {
		return "WorkflowMaster [id=" + id + ", workflowName=" + workflowName + "]";
	}

	
	
//	public Set<WorkflowTrackMaster> getWorkflowTrackList() {
//		return WorkflowTrackList;
//	}
//
//	public void setWorkflowTrackList(Set<WorkflowTrackMaster> workflowTrackList) {
//		this.fieldConstraintList.clear();
//	    if (fieldConstraintList != null) {
//	        this.fieldConstraintList.addAll(fieldConstraintList);
//	    }
//		WorkflowTrackList = workflowTrackList;
//	}

	
	
	
	
}
