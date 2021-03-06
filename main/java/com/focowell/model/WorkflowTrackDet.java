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
@Table(name="workflow_track_det")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkflowTrackDet {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "workflow_track_det_seq")
    @SequenceGenerator(name = "workflow_track_det_seq",allocationSize = 1, sequenceName = "DB_WF_TRACK_DET_SEQ")
    private long id;
	
	@Column(name="ACCESS_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date accessDate=new Date();
	
	@JsonIgnoreProperties(value="workflowTrackDetList",allowSetters=true)
	@ManyToOne(fetch=FetchType.LAZY)    
	private User accessUser;
	
	@JsonIgnoreProperties(value="workflowTrackDetList",allowSetters=true)
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE} ,fetch=FetchType.LAZY)    
	private WorkflowTrackMaster workflowTrackMaster;
	
	@JsonIgnoreProperties(value="workflowTrackDetList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)    
	private WorkflowNode workflowNode;
	
	
	
	@Column(name="open")
	private boolean open;

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

	public User getAccessUser() {
		return accessUser;
	}

	public void setAccessUser(User accessUser) {
		this.accessUser = accessUser;
	}

	public WorkflowTrackMaster getWorkflowTrackMaster() {
		return workflowTrackMaster;
	}

	public void setWorkflowTrackMaster(WorkflowTrackMaster workflowTrackMaster) {
		this.workflowTrackMaster = workflowTrackMaster;
	}

	public WorkflowNode getWorkflowNode() {
		return workflowNode;
	}

	public void setWorkflowNode(WorkflowNode workflowNode) {
		this.workflowNode = workflowNode;
	}



	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkflowTrackDet other = (WorkflowTrackDet) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
	
}
