package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.WorkflowTrackMaster;


@Repository
public interface WorkflowTrackMasterDao extends CrudRepository<WorkflowTrackMaster, Long> {
//	List<WorkflowTrackMaster> findByActionName(String actionName);
	
	@Query("SELECT tm FROM WorkflowTrackMaster tm left join fetch tm.workflowMaster wm left join fetch tm.requestedUser where wm.id=:workflowId order by tm.id")
	Iterable<WorkflowTrackMaster> findAllByWorkflowIdJPQL(@Param("workflowId") long workflowId);
	
	@Query("SELECT tm FROM WorkflowTrackMaster tm left join fetch tm.workflowMaster wm left join fetch wm.process p left join fetch tm.requestedUser where p.id=:processId order by tm.id")
	Iterable<WorkflowTrackMaster> findAllByProcessIdJPQL(@Param("processId") long processId);

	@Query("SELECT tm FROM WorkflowTrackMaster tm left join fetch tm.workflowMaster wm left join fetch wm.process p left join fetch tm.requestedUser u where u.id=:userId order by tm.id")
	List<WorkflowTrackMaster> findAllByUserJPQL(long userId);
}
