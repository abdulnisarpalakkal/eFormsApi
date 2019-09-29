package com.focowell.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.focowell.model.VirtualTableConstraints;
import com.focowell.model.VirtualTableMaster;

public class VirtualTableFKConstraintRefDto implements Serializable {

	List<VirtualTableMaster> refTables;
	List<VirtualTableConstraints> refPkConstraints;
	
	public List<VirtualTableMaster> getRefTables() {
		return refTables;
	}
	public void setRefTables(List<VirtualTableMaster> refTables) {
		this.refTables = refTables;
	}
	public List<VirtualTableConstraints> getRefPkConstraints() {
		return refPkConstraints;
	}
	public void setRefPkConstraints(List<VirtualTableConstraints> refPkConstraints) {
		this.refPkConstraints = refPkConstraints;
	}
	
	
}
