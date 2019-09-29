package com.focowell.dto;

import java.util.Set;

import com.focowell.model.FormComponentRefValue;

public class FormComponentDto {
	private long id;
	private String componentLabel;
	private String componentValue;
	private Set<FormComponentRefValue> componentRefValues;
	private VirtualTableFieldDto virtualTableField;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getComponentLabel() {
		return componentLabel;
	}
	public void setComponentLabel(String componentLabel) {
		this.componentLabel = componentLabel;
	}
	public String getComponentValue() {
		return componentValue;
	}
	public void setComponentValue(String componentValue) {
		this.componentValue = componentValue;
	}
	public Set<FormComponentRefValue> getComponentRefValues() {
		return componentRefValues;
	}
	public void setComponentRefValues(Set<FormComponentRefValue> componentRefValues) {
		this.componentRefValues = componentRefValues;
	}
	public VirtualTableFieldDto getVirtualTableField() {
		return virtualTableField;
	}
	public void setVirtualTableField(VirtualTableFieldDto virtualTableField) {
		this.virtualTableField = virtualTableField;
	}
	
}
