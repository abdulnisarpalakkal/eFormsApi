package com.focowell.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.WorkflowNode;

@Repository
public interface WorkflowNodeDao extends CrudRepository<WorkflowNode, Long> {
	@Query("SELECT v FROM WorkflowNode v left join fetch v.formMaster f left join fetch v.actionEventObjects a  left join fetch v.workflowMaster w left join fetch v.childWorkflow where w.id=:workflowId ")
	Iterable<WorkflowNode> findAllByWorkflowIdJPQL(@Param("workflowId") long workflowId);
	
	Iterable<WorkflowNode> findAllByWorkflowMaster_Id( long workflowId);

	@Modifying
	@Query("delete FROM WorkflowNode v where v.workflowMaster.id=:workflowId ")
	void deleteByWorkflowIdJPQL(@Param("workflowId") long workflowId);
}
