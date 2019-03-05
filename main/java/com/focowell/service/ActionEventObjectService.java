package com.focowell.service;

import java.util.List;

import com.focowell.model.ActionEventObject;

public interface ActionEventObjectService {
	    List<ActionEventObject> findAll();
	    void delete(long id);
	    ActionEventObject findOne(String actionEventObjectName);

	    ActionEventObject findById(Long id);
	    ActionEventObject save(ActionEventObject actionEventObject) ;
	    ActionEventObject update(ActionEventObject actionEventObject);
		void removeByWorkflow(long workflowId);
}
