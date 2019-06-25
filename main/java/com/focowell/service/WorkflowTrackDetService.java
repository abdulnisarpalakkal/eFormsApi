package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.WorkflowTrackDet;

public interface WorkflowTrackDetService {
	    List<WorkflowTrackDet> findAll();
	    List<WorkflowTrackDet> findAllByOpen(boolean open);
	    List<WorkflowTrackDet> findAllByUser();
	    void delete(long id);
	    WorkflowTrackDet findOne(String workflowTrackDetName);

	    WorkflowTrackDet findById(Long id);
	    WorkflowTrackDet save(WorkflowTrackDet workflowTrackDet) ;
	    WorkflowTrackDet update(WorkflowTrackDet workflowTrackDet);
		WorkflowTrackDet updateOpenStatus(WorkflowTrackDet workflowTrackDet);
		List<WorkflowTrackDet> findAllByProcess(long processId);
		List<WorkflowTrackDet> findAllByWorkflowTrackMaster(Long workflowTrackMasterId);
		
		
		
}
