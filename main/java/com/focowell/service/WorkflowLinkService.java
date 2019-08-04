package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowNode;

public interface WorkflowLinkService {
	    List<WorkflowLink> findAll();
	    List<WorkflowLink> findAllByWorkflow(long workflowId);
	    void delete(long id);
	    void removeByWorkflow(long workflowId);
	    WorkflowLink findOne(String workflowLinkName);

	    WorkflowLink findById(Long id);
	    WorkflowLink save(WorkflowLink workflowLink) throws AlreadyExistsException;
	    boolean saveAll(List<WorkflowLink> workflowLinks) throws AlreadyExistsException;
	    WorkflowLink update(WorkflowLink workflowLink);
	    List<WorkflowLink> findBySourceId(long sourceNodeid);
	    WorkflowLink findStartNodeByWorkflow(long workflowId);
		
		
}
