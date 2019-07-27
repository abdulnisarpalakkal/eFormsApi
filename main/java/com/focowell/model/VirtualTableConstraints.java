package com.focowell.model;

import java.io.Serializable;
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

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.focowell.model.deserializer.VirtualTableConstraintsDeserializer;

@Entity  
@Table(name="virtual_table_constraints")
//@JsonDeserialize(using = VirtualTableConstraintsDeserializer.class)
public class VirtualTableConstraints implements Serializable {
	
	
	public VirtualTableConstraints() {
		
	}

	public VirtualTableConstraints(long id, VirtualTableConstraintType constraintType, String constraintValue,
			VirtualTableField virtualTableField, VirtualTableConstraints foreignConstraint) {
		super();
		this.id = id;
		this.constraintType = constraintType;
		this.constraintValue = constraintValue;
		this.virtualTableField = virtualTableField;
		this.foreignConstraint = foreignConstraint;
	}

	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "v_table_constr_seq")
    @SequenceGenerator(name = "v_table_constr_seq",allocationSize = 1, sequenceName = "DB_V_TABLE_CONSTR_SEQ")
    private long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="CONSTRAINT_TYPE")
    private VirtualTableConstraintType constraintType;
	
	
	@Column(name="CONSTRAINT_VALUE")
    private String constraintValue;
	
//	@JsonIgnore
	@JsonIgnoreProperties(value="fieldConstraintList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER) 
    private VirtualTableField virtualTableField;
	
	@JsonIgnoreProperties(value="childConstraintList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER) 
    private VirtualTableConstraints foreignConstraint;
	
	
	@JsonIgnoreProperties(value="foreignConstraint",allowSetters=true)
	@OneToMany(fetch=FetchType.LAZY, mappedBy="foreignConstraint",cascade=CascadeType.REMOVE) 
	private Set<VirtualTableConstraints> childConstraintList;

	@Transient
	@JsonSerialize
	@JsonDeserialize
	private boolean deleted;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public VirtualTableConstraintType getConstraintType() {
		return constraintType;
	}

	public void setConstraintType(VirtualTableConstraintType constraintType) {
		this.constraintType = constraintType;
	}

	public String getConstraintValue() {
		return constraintValue;
	}

	public void setConstraintValue(String constraintValue) {
		this.constraintValue = constraintValue;
	}

	public VirtualTableField getVirtualTableField() {
		return virtualTableField;
	}

	public void setVirtualTableField(VirtualTableField virtualTableField) {
		this.virtualTableField = virtualTableField;
	}

	

	public VirtualTableConstraints getForeignConstraint() {
		return foreignConstraint;
	}

	public void setForeignConstraint(VirtualTableConstraints foreignConstraint) {
		this.foreignConstraint = foreignConstraint;
	}

	public Set<VirtualTableConstraints> getChildConstraintList() {
		return childConstraintList;
	}

	public void setChildConstraintList(Set<VirtualTableConstraints> childConstraintList) {
		this.childConstraintList = childConstraintList;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constraintType == null) ? 0 : constraintType.hashCode());
		result = prime * result + ((virtualTableField == null) ? 0 : virtualTableField.hashCode());
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
		VirtualTableConstraints other = (VirtualTableConstraints) obj;
		if (constraintType != other.constraintType)
			return false;
		if (virtualTableField == null) {
			if (other.virtualTableField != null)
				return false;
		} else if (!virtualTableField.equals(other.virtualTableField))
			return false;
		return true;
	}

	
	
	
}
