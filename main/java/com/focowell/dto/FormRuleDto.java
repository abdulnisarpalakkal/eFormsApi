package com.focowell.dto;

import java.util.Set;

import com.focowell.model.FormRuleParameterValue;
import com.focowell.model.FormRuleType;

public class FormRuleDto {
	private long id;
	private String formRuleName;
	private FormRuleType formRuleType;
	private Set<FormRuleParameterValueDto> formRuleParameterValues;
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
	public FormRuleType getFormRuleType() {
		return formRuleType;
	}
	public void setFormRuleType(FormRuleType formRuleType) {
		this.formRuleType = formRuleType;
	}
	public Set<FormRuleParameterValueDto> getFormRuleParameterValues() {
		return formRuleParameterValues;
	}
	public void setFormRuleParameterValues(Set<FormRuleParameterValueDto> formRuleParameterValues) {
		this.formRuleParameterValues = formRuleParameterValues;
	}
	

}
