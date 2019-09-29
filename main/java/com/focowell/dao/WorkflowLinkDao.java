package com.focowell.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowNode;
import com.focowell.model.WorkflowNodeType;

@Repository
public interface WorkflowLinkDao extends CrudRepository<WorkflowLink, Long> {
	@Query("SELECT v FROM WorkflowLink v left join fetch v.sourceNode s left join fetch v.targetNode t "
			+ " left join fetch t.formMaster form left join fetch form.virtualTableMaster  "
			+ "left join fetch v.workflowMaster w where w.id=:workflowId ")
	Iterable<WorkflowLink> findAllByWorkflowIdJPQL(@Param("workflowId") long workflowId);
	
	@Query("SELECT v FROM WorkflowLink v left join fetch v.sourceNode s left join fetch v.targetNode t  left join fetch v.workflowMaster w where w.id=:workflowId and s.nodeType=:startType ")
	WorkflowLink findFirstLinkByWorkflowIdJPQL(@Param("workflowId") long workflowId,@Param("startType") WorkflowNodeType type);
	
	@EntityGraph("link-entity-graph")
	List<WorkflowLink> findBySourceNode_NodeId(long sourceNodeid);
	Long removeByWorkflowMaster_Id(long workflowId);
	
	@Modifying
	@Query("delete FROM WorkflowLink v where v.workflowMaster.id=:workflowId ")
	void deleteByWorkflowId(@Param("workflowId") long workflowId);

}
