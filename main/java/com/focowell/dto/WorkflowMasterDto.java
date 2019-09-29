package com.focowell.dto;

import java.util.Date;

public class WorkflowMasterDto {
	private long id;
	private String workflowName;
	private String workflowDesc;
	private boolean published;
	private Date publishDate;
	private boolean child;
	private ProcessDataDto process;
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
	public boolean isChild() {
		return child;
	}
	public void setChild(boolean child) {
		this.child = child;
	}
	public ProcessDataDto getProcess() {
		return process;
	}
	public void setProcess(ProcessDataDto process) {
		this.process = process;
	}
	
}
