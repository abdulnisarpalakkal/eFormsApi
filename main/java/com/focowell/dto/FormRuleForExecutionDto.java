package com.focowell.dto;

import java.util.Set;

public class FormRuleForExecutionDto {
	private long id;
	private String formRuleName;
	private FormRuleTypeForExecutionDto formRuleType;
	private Set<FormRuleParameterValueForExecutionDto> formRuleParameterValues;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFormRuleName() {
		return formRuleName;
	}
	public void setFormRuleName(String formRuleName) {
		this.formRuleName = formRuleName;
	}
	public FormRuleTypeForExecutionDto getFormRuleType() {
		return formRuleType;
	}
	public void setFormRuleType(FormRuleTypeForExecutionDto formRuleType) {
		this.formRuleType = formRuleType;
	}
	public Set<FormRuleParameterValueForExecutionDto> getFormRuleParameterValues() {
		return formRuleParameterValues;
	}
	public void setFormRuleParameterValues(Set<FormRuleParameterValueForExecutionDto> formRuleParameterValues) {
		this.formRuleParameterValues = formRuleParameterValues;
	}
	
}
