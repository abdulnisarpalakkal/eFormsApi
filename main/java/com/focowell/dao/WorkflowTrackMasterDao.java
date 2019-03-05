package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.WorkflowTrackMaster;


@Repository
public interface WorkflowTrackMasterDao extends CrudRepository<WorkflowTrackMaster, Long> {
//	List<WorkflowTrackMaster> findByActionName(String actionName);
	
//	@Query("SELECT a FROM WorkflowTrackMaster  ")
//	Iterable<WorkflowTrackMaster> findAllByJPQL();
}
