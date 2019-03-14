package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.WorkflowTrackDet;


@Repository
public interface WorkflowTrackDetDao extends CrudRepository<WorkflowTrackDet, Long> {
	List<WorkflowTrackDet> findAllByOpen (boolean open);
	
	@Query("SELECT wt FROM WorkflowTrackDet wt left join fetch wt.accessUser u left join fetch wt.workflowTrackMaster wm "
			+ " left join fetch wt.workflowNode where u.id=:userId ")
	List<WorkflowTrackDet> findAllByUserJPQL (@Param("userId") long userId);
	
//	@Query("SELECT a FROM WorkflowTrackDet  ")
//	Iterable<WorkflowTrackDet> findAllByJPQL();
}
