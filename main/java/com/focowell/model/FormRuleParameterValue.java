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
@Table(name="form_rule_parameter_value")
public class FormRuleParameterValue {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="formRuleParamValueSeq")
	@SequenceGenerator(name="formRuleParamValueSeq",sequenceName="form_rule_param_value_seq")
	public long id;
	
	@JsonIgnoreProperties(value="formRuleParameterValues",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)
	public FormRule formRule;
	
	@JsonIgnoreProperties(value="formRuleParameterValues",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)
	public FormRuleTypeParameter formRuleTypeParameter;
	
	@Column(name="form_rule_parameter_value")
	public String formRuleParameterValue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public FormRule getFormRule() {
		return formRule;
	}
	public void setFormRule(FormRule formRule) {
		this.formRule = formRule;
	}
	public FormRuleTypeParameter getFormRuleTypeParameter() {
		return formRuleTypeParameter;
	}
	public void setFormRuleTypeParameter(FormRuleTypeParameter formRuleTypeParameter) {
		this.formRuleTypeParameter = formRuleTypeParameter;
	}
	public String getFormRuleParameterValue() {
		return formRuleParameterValue;
	}
	public void setFormRuleParameterValue(String formRuleParameterValue) {
		this.formRuleParameterValue = formRuleParameterValue;
	}
		
	
}
