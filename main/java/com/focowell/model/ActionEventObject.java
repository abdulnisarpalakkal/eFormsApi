package com.focowell.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity  
@Table(name="action_event_object")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActionEventObject {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "action_event_seq")
    @SequenceGenerator(name = "action_event_seq",allocationSize = 1, sequenceName = "db_action_event_seq")
    private Long id;
	
//	@JsonManagedReference
	@JsonIgnoreProperties("actionEventObjects")
	@ManyToOne(fetch=FetchType.EAGER)    
	private ActionEvent actionEvent;
	
//	@JsonBackReference
	@JsonIgnoreProperties("actionEventObjects")
	@ManyToOne(fetch=FetchType.LAZY)    
	private WorkflowNode workflowNode;
	
//	@JsonManagedReference
	
	@JsonIgnoreProperties("actionEventObject")
	@OneToMany(mappedBy="actionEventObject",cascade= CascadeType.ALL, fetch=FetchType.EAGER)
	public Set<ActionEventParamObject> actionEventParamObjects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public WorkflowNode getWorkflowNode() {
		return workflowNode;
	}

	public void setWorkflowNode(WorkflowNode workflowNode) {
		this.workflowNode = workflowNode;
	}

	public Set<ActionEventParamObject> getActionEventParamObjects() {
		return actionEventParamObjects;
	}

	public void setActionEventParamObjects(Set<ActionEventParamObject> actionEventParamObjects) {
		this.actionEventParamObjects = actionEventParamObjects;
	}
	
	
}
