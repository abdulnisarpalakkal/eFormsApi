package com.focowell.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity  
@Table(name="USER_DATA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "user_Sequence")
    @SequenceGenerator(name = "user_Sequence", sequenceName = "DB_USER_SEQ")
    private long id;
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;
    
    @NotEmpty
    @Column(name="FIRST_NAME", nullable=false)
    private String firstName;
 
   
    @Column(name="LAST_NAME")
    private String lastName;
 
    @NotEmpty
    @Column(name="EMAIL", nullable=false)
    private String email;
 
    
    @NotEmpty
 
    @ManyToMany(fetch = FetchType.EAGER,cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "USER_USER_ROLE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_ROLE_ID") })
   
    private Set<UserRoles> userRoles = new HashSet<UserRoles>();
    
    @JsonIgnore
    @OneToMany( mappedBy="createdUser")
    public Set<Category> createdCategories;
    
    @JsonIgnore
    @OneToMany( mappedBy="createdUser")
    public Set<ProcessData> createdProcesses;
    
    @JsonIgnore
    @ManyToMany(mappedBy="accessUsers")
    private Set<FormMaster> forms = new HashSet<FormMaster>();
    
    @JsonIgnoreProperties("requestedUser")
    @ManyToMany(mappedBy="requestedUser")
    private Set<WorkflowTrackMaster> workflowTrackList ;
    
    public Set<Category> getCreatedCategories() {
		return createdCategories;
	}

	public void setCreatedCategories(Set<Category> createdCategories) {
		this.createdCategories = createdCategories;
	}

	

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<ProcessData> getCreatedProcesses() {
		return createdProcesses;
	}

	public void setCreatedProcesses(Set<ProcessData> createdProcesses) {
		this.createdProcesses = createdProcesses;
	}

	public Set<FormMaster> getForms() {
		return forms;
	}

	public void setForms(Set<FormMaster> forms) {
		this.forms = forms;
	}

	public Set<WorkflowTrackMaster> getWorkflowTrackList() {
		return workflowTrackList;
	}

	public void setWorkflowTrackList(Set<WorkflowTrackMaster> workflowTrackList) {
		this.workflowTrackList = workflowTrackList;
	}

	

  
}
