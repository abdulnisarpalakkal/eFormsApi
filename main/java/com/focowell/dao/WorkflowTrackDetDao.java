package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.WorkflowTrackDet;


@Repository
public interface WorkflowTrackDetDao extends CrudRepository<WorkflowTrackDet, Long> {
	List<WorkflowTrackDet> findAllByOpen (boolean open);
	
//	@Query("SELECT a FROM WorkflowTrackDet  ")
//	Iterable<WorkflowTrackDet> findAllByJPQL();
}
