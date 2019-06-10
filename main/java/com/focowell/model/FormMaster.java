package com.focowell.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.focowell.model.dto.VirtualRowRecordsDto;

@Entity  
@Table(name="form_master")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FormMaster {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "form_master_seq")
    @SequenceGenerator(name = "form_master_seq",allocationSize = 1, sequenceName = "DB_FORM_MASTER_SEQ")
    private long id;
	
	@NotEmpty
	@Column(name="FORM_NAME", nullable=false)
	@Size(min=2)
    private String formName;
	
	@Column(name="FORM_DESC")
    private String formDesc;
	
	@Column(name="OPEN")
    private boolean open;
	
	@JsonIgnoreProperties(value="formMasterList",allowSetters=true)
	@ManyToOne(fetch=FetchType.LAZY)    
	private VirtualTableMaster virtualTableMaster;
	
	@JsonIgnoreProperties(value="formMaster",allowSetters=true) 
	@OneToMany( mappedBy="formMaster",cascade=CascadeType.ALL,orphanRemoval=true)   
	public Set<FormDesign> formDesignList;
	
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "FORM_USER_ACCESS", 
             joinColumns = { @JoinColumn(name = "FORM_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
   
    private Set<User> accessUsers = new HashSet<User>();
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE  )
    @JoinTable(name = "FORM_GROUP_ACCESS", 
             joinColumns = { @JoinColumn(name = "FORM_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
   
    private Set<UserRoles> accessGroups = new HashSet<UserRoles>();
	
	@JsonIgnoreProperties(value="formMaster",allowSetters=true) 
	@OneToMany(mappedBy="formMaster",fetch=FetchType.LAZY)  
	private Set<WorkflowNode> workflowNodeList;
	
//	@JsonIgnoreProperties(value="formMaster",allowSetters=true) 
	@JsonIgnore
	@OneToMany(mappedBy="formMaster",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)  
	private Set<FormRule> formRules;
	
	@Transient
	@JsonSerialize
	@JsonDeserialize 
	private VirtualRowRecordsDto virtualRowRecordsDto;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormDesc() {
		return formDesc;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	public VirtualTableMaster getVirtualTableMaster() {
		return virtualTableMaster;
	}

	public void setVirtualTableMaster(VirtualTableMaster virtualTableMaster) {
		this.virtualTableMaster = virtualTableMaster;
	}

	public Set<FormDesign> getFormDesignList() {
		return formDesignList;
	}

	public void setFormDesignList(Set<FormDesign> formDesignList) {
		this.formDesignList = formDesignList;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public Set<User> getAccessUsers() {
		return accessUsers;
	}

	public void setAccessUsers(Set<User> accessUsers) {
		this.accessUsers = accessUsers;
	}

	public Set<UserRoles> getAccessGroups() {
		return accessGroups;
	}

	public void setAccessGroups(Set<UserRoles> accessGroups) {
		this.accessGroups = accessGroups;
	}

	public Set<WorkflowNode> getWorkflowNodeList() {
		return workflowNodeList;
	}

	public void setWorkflowNodeList(Set<WorkflowNode> workflowNodeList) {
		this.workflowNodeList = workflowNodeList;
	}

	

	public VirtualRowRecordsDto getVirtualRowRecordsDto() {
		return virtualRowRecordsDto;
	}

	public void setVirtualRowRecordsDto(VirtualRowRecordsDto virtualRowRecordsDto) {
		this.virtualRowRecordsDto = virtualRowRecordsDto;
	}

	public Set<FormRule> getFormRules() {
		return formRules;
	}

	public void setFormRules(Set<FormRule> formRules) {
		this.formRules = formRules;
	}

	@Override
	public String toString() {
		return "FormMaster [id=" + id + ", formName=" + formName + ", virtualTableMaster=" + virtualTableMaster + "]";
	}

	
	
	
	
	
}
