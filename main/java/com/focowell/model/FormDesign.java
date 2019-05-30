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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FormDesign {
	public FormDesign() {
		
	}
	public FormDesign(String componentName, String componentLabel, FormComponentType componentType
			,int alignOrder, VirtualTableField virtualTableField ) {
		this.componentName=componentName;
		this.componentLabel=componentLabel;
		this.componentType=componentType;
		this.alignOrder=alignOrder;
//		this.componentRefValues=componentRefValues;
		this.virtualTableField=virtualTableField;
		
	}
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "form_design_seq")
    @SequenceGenerator(name = "form_design_seq",initialValue=101,allocationSize = 1, sequenceName = "DB_FORM_DESIGN_SEQ")
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
	
	@JsonIgnoreProperties(value={"formDesigns","formMaster"},allowSetters=true)
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JoinTable(name="form_design_rules",
			joinColumns= {@JoinColumn(name="form_design_id")},
			inverseJoinColumns= {@JoinColumn(name="form_rule_id")})
	private Set<FormRule> formRules;

	@Transient
	@JsonSerialize
	@JsonDeserialize
	List<Map<String,VirtualTableRecords>> gridRecords;
	
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

	public Set<FormRule> getFormRules() {
		return formRules;
	}

	public void setFormRules(Set<FormRule> formRules) {
		this.formRules = formRules;
	}
	public List<Map<String, VirtualTableRecords>> getGridRecords() {
		return gridRecords;
	}
	public void setGridRecords(List<Map<String, VirtualTableRecords>> gridRecords) {
		this.gridRecords = gridRecords;
	}

	

}
