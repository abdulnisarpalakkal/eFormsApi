package com.focowell.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity  
@Table(name="action_event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActionEvent {

	@Id  
    private Long id;
	
	@NotEmpty
	@Column(name="ACTION_NAME", nullable=false)
	@Size(min=5)
    private String actionName;
	
	@Column(name="ACTION_DESC")
    private String actionDesc;
	
//	@JsonManagedReference
	@JsonIgnoreProperties("actionEvent")
	@OneToMany(mappedBy="actionEvent", fetch=FetchType.EAGER)
	public Set<ActionEventParam> actionEventParams;
	
//	@JsonBackReference
	
	@JsonIgnoreProperties("actionEvent")
	@OneToMany(mappedBy="actionEvent")
	public Set<ActionEventObject> actionEventObjects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public Set<ActionEventParam> getActionEventParams() {
		return actionEventParams;
	}

	public void setActionEventParams(Set<ActionEventParam> actionEventParams) {
		this.actionEventParams = actionEventParams;
	}

	public Set<ActionEventObject> getActionEventObjects() {
		return actionEventObjects;
	}

	public void setActionEventObjects(Set<ActionEventObject> actionEventObjects) {
		this.actionEventObjects = actionEventObjects;
	}
	
	
}
