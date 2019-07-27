package com.focowell.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
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
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.focowell.model.deserializer.VirtualTableFieldDeserializer;

@Entity  
@Table(name="virtual_table_fields"
//,indexes= {@Index(name="virtual_table_master_index",columnList="virtual_table_master_id",unique=false)}
)
@JsonDeserialize(using = VirtualTableFieldDeserializer.class)

public class VirtualTableField implements Serializable {
	
//	private static final long serialVersionUID = 1L;

	public VirtualTableField(long id,  String fieldName,String fieldDesc,boolean deleted,
			VirtualTableFieldDataType fieldDataType,  
			VirtualTableMaster virtualTableMaster, Set<FormComponent> formComponentList,
			Set<VirtualTableConstraints> fieldConstraintList) {
		super();
		this.id = id;
		this.fieldName = fieldName;
		this.fieldDataType = fieldDataType;
		this.fieldDesc = fieldDesc;
		this.deleted=deleted;
		
		this.virtualTableMaster = virtualTableMaster;
		this.formComponentList = formComponentList;
		this.fieldConstraintList = fieldConstraintList;
//		this.refConstraintList = refConstraintList;
	}
	
	public VirtualTableField() {
		super();
		
	}

	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "v_table_field_seq")
    @SequenceGenerator(name = "v_table_field_seq",allocationSize = 1, sequenceName = "DB_V_TABLE_FIELD_SEQ")
    private long id;
	
	

	@NotEmpty
	@Column(name="FIELD_NAME", nullable=false)
	@Size(min=2, message="{process.firstName.size}")
    private String fieldName;
	
	
	@Column(name="FIELD_DATA_TYPE")
	@Enumerated(EnumType.ORDINAL)
    private VirtualTableFieldDataType fieldDataType;
	
	@Column(name="FIELD_DESC")
    private String fieldDesc;
	
	@Transient
	private boolean deleted;
	
	@JsonIgnoreProperties(value="virtualTableFieldsList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)    
	private VirtualTableMaster virtualTableMaster;
	
	@JsonIgnoreProperties("virtualTableField")
	@OneToMany( mappedBy="virtualTableField")   
	public Set<FormComponent> formComponentList;

//	@JsonIgnore
	@JsonIgnoreProperties(value="virtualTableField",allowSetters=true) 
	@OneToMany( mappedBy="virtualTableField" ,fetch=FetchType.EAGER, cascade=CascadeType.MERGE,orphanRemoval=true)
	public Set<VirtualTableConstraints> fieldConstraintList;
	
	@JsonIgnore
    @ManyToMany(mappedBy="virtualTableFields")
    private Set<FormGrid> formGrids ;
	
//	@JsonIgnoreProperties(value="refVirtualTableField",allowSetters=true)
//	@OneToMany( mappedBy="refVirtualTableField" ,fetch=FetchType.LAZY)
//	public Set<VirtualTableConstraints> refConstraintList; //foreign key

//	@JsonIgnoreProperties(value="virtualTableFields",allowSetters=true)
//	@OneToMany( mappedBy="virtualTableFields" ,fetch=FetchType.LAZY, cascade=CascadeType.REMOVE,orphanRemoval=true)
//	public Set<VirtualTableRecords> virtualTableRecords;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public VirtualTableFieldDataType getFieldDataType() {
		return fieldDataType;
	}

	public void setFieldDataType(VirtualTableFieldDataType fieldDataType) {
		this.fieldDataType = fieldDataType;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public VirtualTableMaster getVirtualTableMaster() {
		return virtualTableMaster;
	}

	public void setVirtualTableMaster(VirtualTableMaster virtualTableMaster) {
		this.virtualTableMaster = virtualTableMaster;
	}



	public Set<FormComponent> getFormComponentList() {
		return formComponentList;
	}

	public void setFormComponentList(Set<FormComponent> formComponentList) {
		this.formComponentList = formComponentList;
	}

	public Set<VirtualTableConstraints> getFieldConstraintList() {
//		if(fieldConstraintList!=null && fieldConstraintList.isEmpty())
//			fieldConstraintList=null;
		return fieldConstraintList;
	}

	public void setFieldConstraintList(Set<VirtualTableConstraints> fieldConstraintList) {
//		this.fieldConstraintList=fieldConstraintList;
		if(this.fieldConstraintList!=null)
		{
			this.fieldConstraintList.clear();
		    if (fieldConstraintList != null) {
		        this.fieldConstraintList.addAll(fieldConstraintList);
		    }
		}
		else
			this.fieldConstraintList=fieldConstraintList;
	
	}

//	public Set<VirtualTableConstraints> getRefConstraintList() {
//		if(refConstraintList!=null && refConstraintList.isEmpty())
//			refConstraintList=null;
//		return refConstraintList;
//	}
//
//	public void setRefConstraintList(Set<VirtualTableConstraints> refConstraintList) {
//		this.refConstraintList = refConstraintList;
//	}




	public Set<FormGrid> getFormGrids() {
		return formGrids;
	}

	public void setFormGrids(Set<FormGrid> formGrids) {
		this.formGrids = formGrids;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((virtualTableMaster == null) ? 0 : virtualTableMaster.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VirtualTableField other = (VirtualTableField) obj;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (virtualTableMaster == null) {
			if (other.virtualTableMaster != null)
				return false;
		} else if (!virtualTableMaster.equals(other.virtualTableMaster))
			return false;
		return true;
	}


	


}
