package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowMaster;
import com.focowell.model.WorkflowNode;

public interface WorkflowNodeService {
	    List<WorkflowNode> findAll();
	    List<WorkflowNode> findAllByWorkflow(long workflowId);
	    void delete(long id);
	    WorkflowNode findOne(String workflowNodeName);

	    WorkflowNode findById(Long id);
	    WorkflowNode save(WorkflowNode workflowNode) throws AlreadyExistsException;
	   
	    WorkflowNode update(WorkflowNode workflowNode);
		void removeByWorkflow(long workflowId);
		boolean saveAll(WorkflowMaster workflowMaster)
				throws AlreadyExistsException;
		
		
}
