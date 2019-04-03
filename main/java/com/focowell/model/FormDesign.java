package com.focowell.model;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name="form_design")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FormDesign {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "form_design_seq")
    @SequenceGenerator(name = "form_design_seq",allocationSize = 1, sequenceName = "DB_FORM_DESIGN_SEQ")
    private long id;
	
	@NotEmpty
	@Column(name="COMPONENT_NAME", nullable=false)
	@Size(min=2)
    private String componentName;
	
	@Column(name="COMPONENT_LABEL")
	private String componentLabel;
	
	
	@Column(name="COMPONENT_TYPE")
	@Enumerated(EnumType.STRING)
	private FormComponentType componentType;
	
	@Column(name="ALIGN_ORDER")
	private int alignOrder;
	
	@Column(name="HIDE")
	private boolean hide;
	
	@Column(name="COMPONENT_VALUE")
	private String componentValue;
	
	@JsonIgnoreProperties(value="formDesign",allowSetters=true)
	@OneToMany(mappedBy="formDesign", fetch=FetchType.EAGER,cascade=CascadeType.ALL ,orphanRemoval=true)
	private Set<FormComponentRefValue> componentRefValues;
	
	@JsonIgnoreProperties(value="formDesignList",allowSetters=true)
	@ManyToOne(fetch=FetchType.LAZY)  
	private FormMaster formMaster;
	
	@JsonIgnoreProperties(value="formDesignList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)  
	private VirtualTableField virtualTableField;
	

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

	public FormMaster getFormMaster() {
		return formMaster;
	}

	public void setFormMaster(FormMaster formMaster) {
		this.formMaster = formMaster;
	}

	public int getAlignOrder() {
		return alignOrder;
	}

	public void setAlignOrder(int alignOrder) {
		this.alignOrder = alignOrder;
	}

	public VirtualTableField getVirtualTableField() {
		return virtualTableField;
	}

	public void setVirtualTableField(VirtualTableField virtualTableField) {
		this.virtualTableField = virtualTableField;
	}

	public String getComponentLabel() {
		return componentLabel;
	}

	public void setComponentLabel(String componentLabel) {
		this.componentLabel = componentLabel;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
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

	

}
