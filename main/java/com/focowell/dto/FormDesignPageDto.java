package com.focowell.dto;

import java.util.List;

import com.focowell.model.FormDesign;
import com.focowell.model.FormRule;
import com.focowell.model.FormRuleType;

public class FormDesignPageDto {
	List<FormDesign> formDesigns;
	List<FormRule> formRules;
	List<FormRuleType> formRuleTypes;
	public List<FormDesign> getFormDesigns() {
		return formDesigns;
	}
	public void setFormDesigns(List<FormDesign> formDesigns) {
		this.formDesigns = formDesigns;
	}
	public List<FormRule> getFormRules() {
		return formRules;
	}
	public void setFormRules(List<FormRule> formRules) {
		this.formRules = formRules;
	}
	public List<FormRuleType> getFormRuleTypes() {
		return formRuleTypes;
	}
	public void setFormRuleTypes(List<FormRuleType> formRuleTypes) {
		this.formRuleTypes = formRuleTypes;
	}

	

}
