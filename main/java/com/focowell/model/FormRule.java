package com.focowell.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="form_rule")
public class FormRule {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="formRuleSeq")
	@SequenceGenerator(name="formRuleSeq",sequenceName="form_rule_seq")
	private long id;
	
	@Column(name="form_rule_name")
	private String formRuleName;
	
	@JsonIgnoreProperties(value="formRuleList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)
	private FormRuleType formRuleType;
	
	@JsonIgnoreProperties(value="formRule",allowSetters=true)
	@OneToMany(mappedBy="formRule",fetch=FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<FormRuleParameterValue> formRuleParameterValues;
	
	
	@JsonIgnoreProperties(value="formRules",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)
	private FormMaster formMaster;
	
	@JsonIgnoreProperties(value="formRules",allowSetters=true)
	@ManyToMany(mappedBy="formRules")
	private Set<FormDesign> formDesigns;
	
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
	
	
	public Set<FormRuleParameterValue> getFormRuleParameterValues() {
		return formRuleParameterValues;
	}
	public void setFormRuleParameterValues(Set<FormRuleParameterValue> formRuleParameterValues) {
		this.formRuleParameterValues = formRuleParameterValues;
	}
	public Set<FormDesign> getFormDesigns() {
		
		return formDesigns;
	}
	public void setFormDesigns(Set<FormDesign> formDesigns) {
		this.formDesigns = formDesigns;
	}
	public FormRuleType getFormRuleType() {
		return formRuleType;
	}
	public void setFormRuleType(FormRuleType formRuleType) {
		this.formRuleType = formRuleType;
	}
	public FormMaster getFormMaster() {
		return formMaster;
	}
	public void setFormMaster(FormMaster formMaster) {
		this.formMaster = formMaster;
	}
	
	

}
