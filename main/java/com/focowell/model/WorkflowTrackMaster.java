package com.focowell.model;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name="workflow_track_master")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkflowTrackMaster {
	
	public WorkflowTrackMaster() {
		
	}
	public WorkflowTrackMaster(WorkflowMaster workflowMaster,User requestedUser,long dataId,Set<WorkflowTrackDet> workflowTrackDetList) {
		this.workflowMaster=workflowMaster;
		this.requestedUser=requestedUser;
		this.dataId=dataId;
		this.workflowTrackDetList=workflowTrackDetList;
	}
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "workflow_track_master_seq")
    @SequenceGenerator(name = "workflow_track_master_seq",allocationSize = 1, sequenceName = "DB_WF_TRACK_MASTER_SEQ")
    private long id;
	
		
	@Column(name="TRACK_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date trackDate=new Date();
	
	@ManyToOne(fetch=FetchType.LAZY)    
	private User requestedUser;
	
	@JsonIgnoreProperties(value="workflowTrackList",allowSetters=true)
	@ManyToOne(fetch=FetchType.LAZY) 
	private WorkflowMaster workflowMaster;
	
	
	@JsonIgnoreProperties(value="workflowTrackMaster",allowSetters=true)
	@OneToMany( mappedBy="workflowTrackMaster",cascade = CascadeType.ALL,orphanRemoval=true)   
	public Set<WorkflowTrackDet> workflowTrackDetList;
	
	@JsonIgnoreProperties(value="childWorkflowTrackMaster",allowSetters=true)
	@OneToMany( mappedBy="childWorkflowTrackMaster")   
	public Set<WorkflowTrackDet> parentWorkflowTrackDetList;
	
	@Column(name="data_id")
	private long dataId;
	
	@Column(name="completed")
	private boolean completed;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTrackDate() {
		return trackDate;
	}

	public void setTrackDate(Date trackDate) {
		this.trackDate = trackDate;
	}

	public User getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(User requestedUser) {
		this.requestedUser = requestedUser;
	}

	public WorkflowMaster getWorkflowMaster() {
		return workflowMaster;
	}

	public void setWorkflowMaster(WorkflowMaster workflowMaster) {
		this.workflowMaster = workflowMaster;
	}

	public Set<WorkflowTrackDet> getWorkflowTrackDetList() {
		return workflowTrackDetList;
	}

	public void setWorkflowTrackDetList(Set<WorkflowTrackDet> workflowTrackDetList) {
		this.workflowTrackDetList = workflowTrackDetList;
	}

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Set<WorkflowTrackDet> getParentWorkflowTrackDetList() {
		return parentWorkflowTrackDetList;
	}
	public void setParentWorkflowTrackDetList(Set<WorkflowTrackDet> parentWorkflowTrackDetList) {
		this.parentWorkflowTrackDetList = parentWorkflowTrackDetList;
	}
	@Override
	public String toString() {
		return "WorkflowTrackMaster [id=" + id + ", workflowMaster=" + workflowMaster + ", dataId=" + dataId
				+ ", completed=" + completed + "]";
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + (int) (id ^ (id >>> 32));
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		WorkflowTrackMaster other = (WorkflowTrackMaster) obj;
//		if (id != other.id)
//			return false;
//		return true;
//	}
//	
	
	
	
}
