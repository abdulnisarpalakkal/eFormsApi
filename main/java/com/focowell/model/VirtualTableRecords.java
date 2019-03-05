package com.focowell.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name="virtual_table_records")

public class VirtualTableRecords implements Serializable {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "v_table_records_seq")
    @SequenceGenerator(name = "v_table_records_seq", sequenceName = "DB_V_TABLE_RECORD_SEQ")
    private long id;
	
	
	
	@Column(name="STRING_VALUE")
    private String stringValue;
	
	@Column(name="PK_VALUE")
    private long pkValue;
	
	@JsonIgnoreProperties(value="virtualTableRecords",allowSetters=true)	
	@ManyToOne(fetch=FetchType.EAGER)    
	private VirtualTableField virtualTableFields;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getStringValue() {
		return stringValue;
	}


	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}


	public VirtualTableField getVirtualTableFields() {
		return virtualTableFields;
	}


	public void setVirtualTableFields(VirtualTableField virtualTableFields) {
		this.virtualTableFields = virtualTableFields;
	}


	public long getPkValue() {
		return pkValue;
	}


	public void setPkValue(long pkValue) {
		this.pkValue = pkValue;
	}


}
