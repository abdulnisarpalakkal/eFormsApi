package com.focowell.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="form_grid")
public class FormGrid {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "form_grid_seq")
    @SequenceGenerator(name = "form_grid_seq",initialValue=101,allocationSize = 1, sequenceName = "DB_FORM_GRID_SEQ")
	private long id;
	
	@Column
	private boolean add;
	
	@Column
	private boolean edit;
	
	@Column
	private boolean delete;
	
	@JsonIgnoreProperties(value="formGridList",allowSetters=true)
	@ManyToOne(fetch=FetchType.EAGER)  
	private VirtualTableMaster virtualTableMaster;
	
	
	@JsonIgnoreProperties(value="formDesign",allowSetters=true)
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="form_grid_table_field",joinColumns = {@JoinColumn(name="form_grid_id")}, inverseJoinColumns = {@JoinColumn(name="virtual_table_field_id")})
	private Set<VirtualTableField> virtualTableFields;
	
	@Transient
	@JsonSerialize
	@JsonDeserialize
	List<VirtualRowRecord> gridRecords; 
	
	
	@JsonIgnoreProperties(value="formGrid",allowSetters=true)
	@OneToMany(mappedBy = "formGrid")
	private Set<FormDesign> formDesignList;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public Set<VirtualTableField> getVirtualTableFields() {
		return virtualTableFields;
	}
	public void setVirtualTableFields(Set<VirtualTableField> virtualTableFields) {
		this.virtualTableFields = virtualTableFields;
	}
	public VirtualTableMaster getVirtualTableMaster() {
		return virtualTableMaster;
	}
	public void setVirtualTableMaster(VirtualTableMaster virtualTableMaster) {
		this.virtualTableMaster = virtualTableMaster;
	}
	public List<VirtualRowRecord> getGridRecords() {
		return gridRecords;
	}
	public void setGridRecords(List<VirtualRowRecord> gridRecords) {
		this.gridRecords = gridRecords;
	}
	public Set<FormDesign> getFormDesignList() {
		return formDesignList;
	}
	public void setFormDesignList(Set<FormDesign> formDesignList) {
		this.formDesignList = formDesignList;
	}
	
	
	
}
