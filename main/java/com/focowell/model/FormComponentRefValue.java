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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name="form_component_ref_value")
public class FormComponentRefValue {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "form_component_ref_value_seq")
    @SequenceGenerator(name = "form_component_ref_value_seq",allocationSize = 1, sequenceName = "db_component_ref_value_seq")
    private long id;
	
	@Column(name="REF_VALUE")
	private String refValue;
	
	@JsonIgnoreProperties(value="componentRefValues",allowSetters=true)
	@ManyToOne(fetch=FetchType.LAZY)
	public FormDesign formDesign;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public FormDesign getFormDesign() {
		return formDesign;
	}

	public void setFormDesign(FormDesign formDesign) {
		this.formDesign = formDesign;
	}
	
	
}
