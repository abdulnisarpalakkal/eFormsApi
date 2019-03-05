package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.ActionEventObjectDao;
import com.focowell.dao.UserDao;
import com.focowell.model.ActionEventObject;
import com.focowell.model.User;
import com.focowell.service.ActionEventObjectService;

@Service(value = "actionEventObjectService")
public class ActionEventObjectServiceImpl implements ActionEventObjectService {

	@Autowired
	private ActionEventObjectDao actionEventObjectDao;
	
	@Override
	public List<ActionEventObject> findAll() {
		List<ActionEventObject> list = new ArrayList<>();
		actionEventObjectDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		actionEventObjectDao.deleteById(id);
		
	}
	
	@Override
	public void removeByWorkflow(long workflowId) {
		actionEventObjectDao.deleteByWorkflowIdJPQL(workflowId);
		
	}
	@Override
	public ActionEventObject findOne(String actionEventObjectName) {
		return null;
	}

	@Override
	public ActionEventObject findById(Long id) {
		return actionEventObjectDao.findById(id).get();
	}

	@Override
	public ActionEventObject save(ActionEventObject actionEventObject)  {
		
		return actionEventObjectDao.save(actionEventObject);
	}

	@Override
	public ActionEventObject update(ActionEventObject actionEventObject) {
		ActionEventObject updateActionEventObject =actionEventObjectDao.findById(actionEventObject.getId()).get();
		
					
        return actionEventObjectDao.save(updateActionEventObject);
	}
	
	

}
