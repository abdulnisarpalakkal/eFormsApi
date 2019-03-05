package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.WorkflowMaster;

public interface WorkflowMasterService {
	    List<WorkflowMaster> findAll();
	    void delete(long id);
	    WorkflowMaster findOne(String workflowMasterName);

	    WorkflowMaster findById(Long id);
	    WorkflowMaster save(WorkflowMaster workflowMaster) throws AlreadyExistsException;
	    WorkflowMaster update(WorkflowMaster workflowMaster);
		void saveDesign(WorkflowMaster workflowMaster) throws AlreadyExistsException;
		WorkflowMaster publishWorkflow(WorkflowMaster workflowMaster);
		List<WorkflowMaster> findAllPublished();
}
