package com.focowell.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="form_rule_type_parameter")
public class FormRuleTypeParameter {
	@Id
	private long id;
	
	@JsonIgnoreProperties(value="formRuleTypeParameterList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)
	private FormRuleType formRuleType;
	
	@Column(name="parameter_name")
	private String parameterName;
	
	@Column(name="parameter_label")
	private String ParameterLabel;
	
	@JsonIgnoreProperties(value="formRuleTypeParameter",allowSetters=true)
	@OneToMany(mappedBy="formRuleTypeParameter",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<FormRuleParameterValue> formRuleParameterValues;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public FormRuleType getFormRuleType() {
		return formRuleType;
	}
	public void setFormRuleType(FormRuleType formRuleType) {
		this.formRuleType = formRuleType;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterLabel() {
		return ParameterLabel;
	}
	public void setParameterLabel(String parameterLabel) {
		ParameterLabel = parameterLabel;
	}
	public Set<FormRuleParameterValue> getFormRuleParameterValues() {
		return formRuleParameterValues;
	}
	public void setFormRuleParameterValues(Set<FormRuleParameterValue> formRuleParameterValues) {
		this.formRuleParameterValues = formRuleParameterValues;
	}
	
	

}
