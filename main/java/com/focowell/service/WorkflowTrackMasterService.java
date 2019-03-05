package com.focowell.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.focowell.model.FormMaster;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.WorkflowStage;
import com.focowell.model.WorkflowTrackDet;
import com.focowell.model.WorkflowTrackMaster;

public interface WorkflowTrackMasterService {
	    List<WorkflowTrackMaster> findAll();
	    void delete(long id);
	    WorkflowTrackMaster findOne(String workflowTrackMasterName);

	    WorkflowTrackMaster findById(Long id);
	    WorkflowTrackMaster save(WorkflowTrackMaster workflowTrackMaster) ;
	    WorkflowTrackMaster update(WorkflowTrackMaster workflowTrackMaster);
	   
		WorkflowStage execute(WorkflowStage WorkflowStage);
		WorkflowTrackDet submitAction(WorkflowStage WorkflowStage);

		List<WorkflowStage> findAllOpenWorkflowTracks();
		boolean formIsAccessable(FormMaster form);
		
		long mergeVirtualRecords(FormMaster form, long pkValue);
		
}
