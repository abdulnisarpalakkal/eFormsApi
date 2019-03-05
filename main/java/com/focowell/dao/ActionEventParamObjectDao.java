package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.ActionEventParamObject;

@Repository
public interface ActionEventParamObjectDao extends CrudRepository<ActionEventParamObject, Long> {
	@Modifying
	@Query("delete from ActionEventParamObject evParamObj WHERE evParamObj.id IN (\r\n" + 
			"		    SELECT   evParamObj2.id FROM ActionEventParamObject evParamObj2\r\n" + 
			"		    JOIN evParamObj2.actionEventObject eventObject \r\n" + 
			"		    JOIN eventObject.workflowNode node join node.workflowMaster master\r\n" + 
			"		    WHERE master.id = :workflowId)")
	void deleteByWorkflowIdJPQL(@Param("workflowId") long workflowId);
}
