package com.focowell.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity  
@Table(name="action_event_param_object")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActionEventParamObject {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "action_event_seq")
    @SequenceGenerator(name = "action_event_seq",allocationSize = 1, sequenceName = "db_action_event_seq")
    private Long id;
	
//	@JsonManagedReference
	@JsonIgnoreProperties("actionEventParamObjects")
	@ManyToOne(fetch=FetchType.LAZY)    
	private ActionEventParam actionEventParam;
	
//	@JsonBackReference
	@JsonIgnoreProperties("actionEventParamObjects")
	@ManyToOne(fetch=FetchType.LAZY)    
	private ActionEventObject actionEventObject;
	
	@Column(name="param_value")
	private String paramValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ActionEventParam getActionEventParam() {
		return actionEventParam;
	}

	public void setActionEventParam(ActionEventParam actionEventParam) {
		this.actionEventParam = actionEventParam;
	}

	public ActionEventObject getActionEventObject() {
		return actionEventObject;
	}

	public void setActionEventObject(ActionEventObject actionEventObject) {
		this.actionEventObject = actionEventObject;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	
	
	
}
