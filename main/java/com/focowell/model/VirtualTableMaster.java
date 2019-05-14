package com.focowell.model;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name="virtual_table_master")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VirtualTableMaster implements Serializable {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "v_table_master_seq")
    @SequenceGenerator(name = "v_table_master_seq",allocationSize = 1, sequenceName = "DB_V_TABLE_MASTER_SEQ")
	private long id;
	
	@NotEmpty
	@Column(name="TABLE_NAME", nullable=false)
	@Size(min=2, message="{process.firstName.size}")
    private String tableName;
	
	
	@Column(name="TABLE_DESC")
    private String tableDesc;
	
	@Column(name="AUTO_INCR")
    private boolean autoIncr;
	
	@ManyToOne(fetch=FetchType.LAZY)    
	private ProcessData process;
	
	@ManyToOne(fetch=FetchType.LAZY)    
	private User createdUser;
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval=true)    
	private VirtualTableSequence virtualTableSequence;
	
	@Column(name="CREATED_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone =  TimeZone.getDefault(), locale = Locale.getDefault())
	private Date createdDate=new Date();
	
	@JsonIgnoreProperties(value="virtualTableMaster",allowSetters=true)
    @OneToMany( mappedBy="virtualTableMaster" ,fetch=FetchType.LAZY,cascade = CascadeType.PERSIST,orphanRemoval=true)
    public Set<VirtualTableField> virtualTableFieldsList;

	@JsonIgnoreProperties(value="virtualTableMaster",allowSetters=true)
    @OneToMany( mappedBy="virtualTableMaster" ,fetch=FetchType.LAZY)
    public Set<FormMaster> formMasterList;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	

	public ProcessData getProcess() {
		return process;
	}

	public void setProcess(ProcessData process) {
		this.process = process;
	}

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Set<VirtualTableField> getVirtualTableFieldsList() {
		return virtualTableFieldsList;
	}

	public void setVirtualTableFieldsList(Set<VirtualTableField> virtualTableFieldsList) {
		this.virtualTableFieldsList = virtualTableFieldsList;
	}

	

	public boolean isAutoIncr() {
		return autoIncr;
	}

	public void setAutoIncr(boolean autoIncr) {
		this.autoIncr = autoIncr;
	}

	public VirtualTableSequence getVirtualTableSequence() {
		return virtualTableSequence;
	}

	public void setVirtualTableSequence(VirtualTableSequence virtualTableSequence) {
		this.virtualTableSequence = virtualTableSequence;
	}

	public Set<FormMaster> getFormMasterList() {
		return formMasterList;
	}

	public void setFormMasterList(Set<FormMaster> formMasterList) {
		this.formMasterList = formMasterList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
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
		VirtualTableMaster other = (VirtualTableMaster) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

	
	
	

}
