package com.focowell.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.focowell.model.VirtualTableConstraints;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableMaster;

public class VirtualTableFieldsConstraintDto implements Serializable {

	VirtualTableMaster virtualTable;
	Set<VirtualTableField> virtualTableFields;
	VirtualTableConstraints pkConstraint;
	Set<VirtualTableConstraints> fkConstraints;
	public VirtualTableMaster getVirtualTable() {
		return virtualTable;
	}
	public void setVirtualTable(VirtualTableMaster virtualTable) {
		this.virtualTable = virtualTable;
	}
	public Set<VirtualTableField> getVirtualTableFields() {
		return virtualTableFields;
	}
	public void setVirtualTableFields(Set<VirtualTableField> virtualTableFields) {
		this.virtualTableFields = virtualTableFields;
	}
	public VirtualTableConstraints getPkConstraint() {
		return pkConstraint;
	}
	public void setPkConstraint(VirtualTableConstraints pkConstraint) {
		this.pkConstraint = pkConstraint;
	}
	public Set<VirtualTableConstraints> getFkConstraints() {
		return fkConstraints;
	}
	public void setFkConstraints(Set<VirtualTableConstraints> fkConstraints) {
		this.fkConstraints = fkConstraints;
	}
	
	
	
}
