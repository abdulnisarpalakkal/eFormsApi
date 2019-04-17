package com.focowell.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name="form_rule_type")
public class FormRuleType {
	
	@Id
	private long id;
	
	@Column(name="rule_type_name")
	private String ruleTypeName;
	
	@Column(name="rule_type_desc")
	private String ruleTypeDesc;
	
	@JsonIgnoreProperties(value="formRuleType",allowSetters=true)
	@OneToMany(mappedBy="formRuleType",fetch=FetchType.EAGER)
	public Set<FormRuleTypeParameter> formRuleTypeParameterList;
	
	@JsonIgnoreProperties(value="formRuleType",allowSetters=true)
	@OneToMany(mappedBy="formRuleType",fetch=FetchType.LAZY)
	public Set<FormRule> formRuleList;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRuleTypeName() {
		return ruleTypeName;
	}
	public void setRuleTypeName(String ruleTypeName) {
		this.ruleTypeName = ruleTypeName;
	}
	public String getRuleTypeDesc() {
		return ruleTypeDesc;
	}
	public void setRuleTypeDesc(String ruleTypeDesc) {
		this.ruleTypeDesc = ruleTypeDesc;
	}
	public Set<FormRuleTypeParameter> getFormRuleTypeParameterList() {
		return formRuleTypeParameterList;
	}
	public void setFormRuleTypeParameterList(Set<FormRuleTypeParameter> formRuleTypeParameterList) {
		this.formRuleTypeParameterList = formRuleTypeParameterList;
	}
	public Set<FormRule> getFormRuleList() {
		return formRuleList;
	}
	public void setFormRuleList(Set<FormRule> formRuleList) {
		this.formRuleList = formRuleList;
	}
	
	
}
