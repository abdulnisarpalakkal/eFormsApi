package com.focowell.model;

import java.util.List;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity  
@Table(name="form_design")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FormDesign {
	public FormDesign() {
		
	}
	public FormDesign(String componentName, String componentLabel, FormComponentType componentType
			,int alignOrder, VirtualTableField virtualTableField ) {
	
		this.componentType=componentType;
		this.alignOrder=alignOrder;
		if(this.componentType!=FormComponentType.GRID) {
			this.formComponent=new FormComponent();
			this.formComponent.setComponentLabel(componentLabel);
			this.formComponent.setVirtualTableField(virtualTableField);
		}
	
	}
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "form_design_seq")
    @SequenceGenerator(name = "form_design_seq",initialValue=101,allocationSize = 1, sequenceName = "DB_FORM_DESIGN_SEQ")
    private long id;
	
	@NotEmpty
	@Column(name="COMPONENT_NAME", nullable=false)
	@Size(min=2)
    private String componentName;
	
	
	
	
	@Column(name="COMPONENT_TYPE")
	@Enumerated(EnumType.STRING)
	private FormComponentType componentType;
	
	@Column(name="ALIGN_ORDER")
	private int alignOrder;
	
	@Column(name="HIDE")
	private boolean hide;
	
		
	
	@JsonIgnoreProperties(value="formDesignList",allowSetters=true)
	@ManyToOne(fetch=FetchType.LAZY)  
	private FormMaster formMaster;
	
	
	
	
	
	@JsonIgnoreProperties(value={"formDesigns","formMaster"},allowSetters=true)
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JoinTable(name="form_design_rules",
			joinColumns= {@JoinColumn(name="form_design_id")},
			inverseJoinColumns= {@JoinColumn(name="form_rule_id")})
	private Set<FormRule> formRules;
	
	@JsonIgnoreProperties(value="formDesignList",allowSetters=true)
	@ManyToOne
	private FormGrid formGrid;

	@JsonIgnoreProperties(value="formDesign",allowSetters=true)
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	private FormComponent formComponent;

	
	
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

	

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	
	public Set<FormRule> getFormRules() {
		return formRules;
	}

	public void setFormRules(Set<FormRule> formRules) {
		this.formRules = formRules;
	}
	public FormGrid getFormGrid() {
		return formGrid;
	}
	public void setFormGrid(FormGrid formGrid) {
		this.formGrid = formGrid;
	}
	public FormComponent getFormComponent() {
		return formComponent;
	}
	public void setFormComponent(FormComponent formComponent) {
		this.formComponent = formComponent;
	}
	

	

}
