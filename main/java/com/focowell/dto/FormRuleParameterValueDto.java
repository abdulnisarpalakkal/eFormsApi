package com.focowell.dto;

import com.focowell.model.FormRuleTypeParameter;

public class FormRuleParameterValueDto {
	private long id;
	private FormRuleTypeParameter formRuleTypeParameter;
	private String formRuleParameterValue;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
