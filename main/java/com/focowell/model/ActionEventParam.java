package com.focowell.model;

import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity  
@Table(name="action_event_param")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActionEventParam {

	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "action_event_param_Seq")
    @SequenceGenerator(name = "action_event_param_Seq",allocationSize = 1, sequenceName = "db_action_event_param_Seq")
    private Long id;
	
	@Column(name="param_name")
	private String paramName;
	
	@Column(name="param_label")
	private String paramLabel;
	
//	@JsonBackReference
	@JsonIgnoreProperties("actionEventParams")
	@ManyToOne(fetch=FetchType.LAZY)    
	private ActionEvent actionEvent;
	
//	@JsonBackReference
	@JsonIgnoreProperties("actionEventObject")
	@OneToMany(mappedBy="actionEventObject")
	public Set<ActionEventParamObject> actionEventParamObjects;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public Set<ActionEventParamObject> getActionEventParamObjects() {
		return actionEventParamObjects;
	}

	public void setActionEventParamObjects(Set<ActionEventParamObject> actionEventParamObjects) {
		this.actionEventParamObjects = actionEventParamObjects;
	}

	public String getParamLabel() {
		return paramLabel;
	}

	public void setParamLabel(String paramLabel) {
		this.paramLabel = paramLabel;
	}

	
	
	
}
