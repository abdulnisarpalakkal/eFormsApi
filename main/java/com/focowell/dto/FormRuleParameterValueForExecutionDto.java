package com.focowell.dto;

public class FormRuleParameterValueForExecutionDto {

	private long id;
	private FormRuleTypeParameterForExecutionDto formRuleTypeParameter;
	private String formRuleParameterValue;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public FormRuleTypeParameterForExecutionDto getFormRuleTypeParameter() {
		return formRuleTypeParameter;
	}
	public void setFormRuleTypeParameter(FormRuleTypeParameterForExecutionDto formRuleTypeParameter) {
		this.formRuleTypeParameter = formRuleTypeParameter;
	}
	public String getFormRuleParameterValue() {
		return formRuleParameterValue;
	}
	public void setFormRuleParameterValue(String formRuleParameterValue) {
		this.formRuleParameterValue = formRuleParameterValue;
	}
	
}
