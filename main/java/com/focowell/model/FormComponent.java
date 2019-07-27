package com.focowell.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="form_component")
public class FormComponent {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "v_form_component_seq")
    @SequenceGenerator(name = "v_form_component_seq",allocationSize = 1, sequenceName = "DB_V_FORM_COMPONENT_SEQ")
	private long id;
	
	@Column(name="COMPONENT_LABEL")
	private String componentLabel;
	
	@Column(name="COMPONENT_VALUE")
	private String componentValue;
	
	
	@JsonIgnoreProperties(value="formComponent",allowSetters=true)
	@OneToMany(mappedBy="formComponent", fetch=FetchType.EAGER,cascade=CascadeType.ALL ,orphanRemoval=true)
	private Set<FormComponentRefValue> componentRefValues;
	
	@JsonIgnoreProperties(value="formComponentList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)  
	private VirtualTableField virtualTableField;
	
	@JsonIgnoreProperties(value="formComponent",allowSetters=true)
	@OneToOne(mappedBy ="formComponent")
	private FormDesign formDesign;

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

	public Set<FormComponentRefValue> getComponentRefValues() {
		return componentRefValues;
	}

	public void setComponentRefValues(Set<FormComponentRefValue> componentRefValues) {
		this.componentRefValues = componentRefValues;
	}

	public VirtualTableField getVirtualTableField() {
		return virtualTableField;
	}

	public void setVirtualTableField(VirtualTableField virtualTableField) {
		this.virtualTableField = virtualTableField;
	}

	public String getComponentValue() {
		return componentValue;
	}

	public void setComponentValue(String componentValue) {
		this.componentValue = componentValue;
	}

	public FormDesign getFormDesign() {
		return formDesign;
	}

	public void setFormDesign(FormDesign formDesign) {
		this.formDesign = formDesign;
	}
	
	
}
