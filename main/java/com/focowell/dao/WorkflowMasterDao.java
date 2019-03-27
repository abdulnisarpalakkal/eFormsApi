package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.WorkflowMaster;
import com.focowell.model.WorkflowNode;

@Repository
public interface WorkflowMasterDao extends CrudRepository<WorkflowMaster, Long> {
	List<WorkflowMaster> findByWorkflowName(String workflowMasterName);
	
	@Query("SELECT v FROM WorkflowMaster v left join fetch v.process ")
	Iterable<WorkflowMaster> findAllByJPQL();
	
	@Query("SELECT v FROM WorkflowMaster v left join fetch v.process p where p.id=:processId ")
	Iterable<WorkflowMaster> findAllByProcessJPQL(@Param("processId")long  processId);
	
	@Query("SELECT v FROM WorkflowMaster v left join fetch v.process where v.published=true ")
	Iterable<WorkflowMaster> findAllPublishedByJPQL();
}
