package com.focowell.dto;

import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.focowell.model.FormComponentType;
import com.focowell.model.FormGrid;
import com.focowell.model.FormRule;

public class FormDesignDto {
	private long id;
	private String componentName;
	@Enumerated(EnumType.STRING)
	private FormComponentType componentType;
	private int alignOrder;
	private boolean hide;
	private Set<FormRuleForExecutionDto> formRules;
	private FormGrid formGrid;
	private FormComponentDto formComponent;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public FormComponentType getComponentType() {
		return componentType;
	}
	public void setComponentType(FormComponentType componentType) {
		this.componentType = componentType;
	}
	public int getAlignOrder() {
		return alignOrder;
	}
	public void setAlignOrder(int alignOrder) {
		this.alignOrder = alignOrder;
	}
	public boolean isHide() {
		return hide;
	}
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	
	public Set<FormRuleForExecutionDto> getFormRules() {
		return formRules;
	}
	public void setFormRules(Set<FormRuleForExecutionDto> formRules) {
		this.formRules = formRules;
	}
	public FormGrid getFormGrid() {
		return formGrid;
	}
	public void setFormGrid(FormGrid formGrid) {
		this.formGrid = formGrid;
	}
	public FormComponentDto getFormComponent() {
		return formComponent;
	}
	public void setFormComponent(FormComponentDto formComponent) {
		this.formComponent = formComponent;
	}
	
}
