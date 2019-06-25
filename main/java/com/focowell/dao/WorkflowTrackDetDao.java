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
			+ " left join fetch wt.workflowActionNode left join fetch wt.workflowFormNode where u.id=:userId order by wt.id")
	List<WorkflowTrackDet> findAllByUserJPQL (@Param("userId") long userId);
	
	@Query("SELECT wt FROM WorkflowTrackDet wt left join fetch wt.accessUser u left join fetch wt.workflowTrackMaster wtm "
			+ " left join fetch wt.workflowActionNode left join fetch wt.workflowFormNode left join fetch wtm.workflowMaster wm left join wm.process p where p.id=:processId order by wt.id")
	List<WorkflowTrackDet> findAllByProcessJPQL (@Param("processId") long processId);
	
	@Query("SELECT wt FROM WorkflowTrackDet wt left join fetch wt.accessUser u left join fetch wt.workflowTrackMaster wtm "
			+ " left join fetch wt.workflowActionNode left join fetch wt.workflowFormNode left join fetch wtm.workflowMaster wm left join wm.process p  left join fetch wt.childWorkflowTrackMaster cwt where wtm.id=:workflowTrackMasterId order by wt.id")
	List<WorkflowTrackDet> findAllByWorkflowTrackMasterJPQL (@Param("workflowTrackMasterId") long workflowTrackMasterId);
	
//	@Query("SELECT a FROM WorkflowTrackDet  ")
//	Iterable<WorkflowTrackDet> findAllByJPQL();
}
