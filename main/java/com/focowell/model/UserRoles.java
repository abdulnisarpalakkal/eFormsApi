package com.focowell.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="USER_ROLE")
public class UserRoles implements Serializable {
	
	 @Id @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="user_profile_SEQ")
	 @SequenceGenerator(name="user_profile_SEQ",sequenceName="DB_USER_ROLE_SEQ", allocationSize=1)
	    private Long id; 
	 
	    @Column(name="TYPE", length=50, unique=true, nullable=false)
	    private String type=UserRoleType.USER.getUserRoleType();
	    
	    @Column(name="ROLE_DESC", length=100)
	    private String roleDesc;;
	    
	    @JsonIgnore
	    @ManyToMany(mappedBy="userRoles")
	    private Set<User> users = new HashSet<User>();
		
	    @JsonIgnore
	    @ManyToMany(mappedBy="accessGroups")
	    private Set<FormMaster> forms = new HashSet<FormMaster>();
	    
		public Set<User> getUsers() {
			return users;
		}

		public void setUsers(Set<User> users) {
			this.users = users;
		}

			
		public String getType() {
	        return type;
	    }
	 
	    public void setType(String type) {
	        this.type = type;
	    }
	 
	    public String getRoleDesc() {
			return roleDesc;
		}

		public void setRoleDesc(String roleDesc) {
			this.roleDesc = roleDesc;
		}

		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Set<FormMaster> getForms() {
			return forms;
		}

		public void setForms(Set<FormMaster> forms) {
			this.forms = forms;
		}

		@Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((id == null) ? 0 : id.hashCode());
	        result = prime * result + ((type == null) ? 0 : type.hashCode());
	        return result;
	    }
	 
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (!(obj instanceof UserRoles))
	            return false;
	        UserRoles other = (UserRoles) obj;
	        if (id == null) {
	            if (other.id != null)
	                return false;
	        } else if (!id.equals(other.id))
	            return false;
	        if (type == null) {
	            if (other.type != null)
	                return false;
	        } else if (!type.equals(other.type))
	            return false;
	        return true;
	    }
	 
	    @Override
	    public String toString() {
	        return "UserProfile [id=" + id + ", type=" + type + "]";
	    }
	 
	 
}