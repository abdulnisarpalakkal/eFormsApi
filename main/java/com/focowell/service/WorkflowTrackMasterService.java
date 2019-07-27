package com.focowell.service;

import java.util.List;

import com.focowell.model.FormMaster;
import com.focowell.model.WorkflowStage;
import com.focowell.model.WorkflowTrackMaster;

import javassist.NotFoundException;

public interface WorkflowTrackMasterService {
	    List<WorkflowTrackMaster> findAll();
	    List<WorkflowTrackMaster> findAllByWorkflow(Long workflowId);
	    List<WorkflowTrackMaster> findAllByProcess(Long processId);
	    List<WorkflowTrackMaster> findAllByUser();
	    void delete(long id);
	    WorkflowTrackMaster findOne(String workflowTrackMasterName);

	    WorkflowTrackMaster findById(Long id);
	    WorkflowTrackMaster save(WorkflowTrackMaster workflowTrackMaster) ;
	    WorkflowTrackMaster update(WorkflowTrackMaster workflowTrackMaster);
	   
		WorkflowStage execute(WorkflowStage WorkflowStage) throws NotFoundException;
		void submitAction(WorkflowStage WorkflowStage) throws Exception;

		List<WorkflowStage> findAllOpenWorkflowTracks();
		boolean formIsAccessable(FormMaster form);
		
		long mergeVirtualRecords(FormMaster form, long pkValue) throws Exception;
		WorkflowTrackMaster completeWorkflow(WorkflowTrackMaster workflowTrackMaster);
		
		
		
		
		
}
