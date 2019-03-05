package com.focowell.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity  
@Table(name="virtual_table_sequence")
public class VirtualTableSequence {
	@Id  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "v_table_seq_seq")
    @SequenceGenerator(name = "v_table_seq_seq",allocationSize = 1, sequenceName = "DB_V_TABLE_SEQ_SEQ")
    private long id;
	
	@Column(name="SEQUENCE_NAME")
    private String sequenceName;
	
	@Column(name="LAST_VALUE")
	private long lastValue;

	@JsonIgnore
	@OneToOne( mappedBy="virtualTableSequence" ,fetch=FetchType.LAZY)
    public VirtualTableMaster virtualTableMaster;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public long getLastValue() {
		return lastValue;
	}

	public void setLastValue(long lastValue) {
		this.lastValue = lastValue;
	}

	public VirtualTableMaster getVirtualTableMaster() {
		return virtualTableMaster;
	}

	public void setVirtualTableMaster(VirtualTableMaster virtualTableMaster) {
		this.virtualTableMaster = virtualTableMaster;
	}

	
	
	
	
}
