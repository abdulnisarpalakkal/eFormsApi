package com.focowell.service;

import java.util.List;

import com.focowell.model.ActionEventParamObject;

public interface ActionEventParamObjectService {
	    List<ActionEventParamObject> findAll();
	    void delete(long id);
	    ActionEventParamObject findOne(String actionEventParamObjectName);

	    ActionEventParamObject findById(Long id);
	    ActionEventParamObject save(ActionEventParamObject actionEventParamObject) ;
	    ActionEventParamObject update(ActionEventParamObject actionEventParamObject);
		void removeByWorkflow(long workflowId);
}
