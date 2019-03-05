package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.dao.ActionEventParamObjectDao;
import com.focowell.model.ActionEventParamObject;
import com.focowell.service.ActionEventParamObjectService;

@Service(value = "actionEventParamObjectService")
public class ActionEventParamObjectServiceImpl implements ActionEventParamObjectService {

	@Autowired
	private ActionEventParamObjectDao actionEventParamObjectDao;
	
	@Override
	public List<ActionEventParamObject> findAll() {
		List<ActionEventParamObject> list = new ArrayList<>();
		actionEventParamObjectDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		actionEventParamObjectDao.deleteById(id);
		
	}
	@Override
	public void removeByWorkflow(long workflowId) {
		actionEventParamObjectDao.deleteByWorkflowIdJPQL(workflowId);
		
	}
	@Override
	public ActionEventParamObject findOne(String actionEventParamObjectName) {
		return null;
	}

	@Override
	public ActionEventParamObject findById(Long id) {
		return actionEventParamObjectDao.findById(id).get();
	}

	@Override
	public ActionEventParamObject save(ActionEventParamObject actionEventParamObject)  {
		
		return actionEventParamObjectDao.save(actionEventParamObject);
	}

	@Override
	public ActionEventParamObject update(ActionEventParamObject actionEventParamObject) {
		ActionEventParamObject updateActionEventParamObject =actionEventParamObjectDao.findById(actionEventParamObject.getId()).get();
				
	
					
        return actionEventParamObjectDao.save(updateActionEventParamObject);
	}
	
	

}
