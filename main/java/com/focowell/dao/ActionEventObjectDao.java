package com.focowell.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.ActionEventObject;

@Repository
public interface ActionEventObjectDao extends CrudRepository<ActionEventObject, Long> {
	@Modifying
	@Query("delete from ActionEventObject evObj WHERE evObj.id IN (\r\n" + 
			"		    SELECT evObj2.id FROM ActionEventObject evObj2\r\n" + 
			"		    JOIN evObj2.workflowNode node join node.workflowMaster master\r\n" + 
			"		    WHERE master.id = :workflowId)")
	void deleteByWorkflowIdJPQL(@Param("workflowId") long workflowId);
	
	
}
