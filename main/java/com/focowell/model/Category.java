package com.focowell.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name="PROCESS_CATEGORY")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category implements Serializable {
	

	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "cat_Sequence")
    @SequenceGenerator(name = "cat_Sequence", sequenceName = "DB_CATEGORY_SEQ")
    private long id;
	
	@NotEmpty
	@Column(name="CATEGORY_NAME", nullable=false)
    private String categoryName;
	
	
	@Column(name="CATEGORY_DESC")
    private String categoryDesc;
	
	@ManyToOne(fetch=FetchType.LAZY)    
	private User createdUser;
	
	
	@Column(name="CREATED_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date createdDate=new Date();

	@JsonIgnore
    @OneToMany( mappedBy="category")
    public Set<ProcessData> processes;
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getCategoryDesc() {
		return categoryDesc;
	}


	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
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
	
	public Set<ProcessData> getProcesses() {
		return processes;
	}


	public void setProcesses(Set<ProcessData> processes) {
		this.processes = processes;
	}

}
