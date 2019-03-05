package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.ActionEventDao;
import com.focowell.model.ActionEvent;
import com.focowell.service.ActionEventService;

@Service(value = "actionEventService")
public class ActionEventServiceImpl implements ActionEventService {

	@Autowired
	private ActionEventDao actionEventDao;
	
	@Override
	public List<ActionEvent> findAll() {
		List<ActionEvent> list = new ArrayList<>();
		actionEventDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		actionEventDao.deleteById(id);
		
	}

	@Override
	public ActionEvent findOne(String actionEventName) {
		return actionEventDao.findByActionName(actionEventName).get(0);
	}

	@Override
	public ActionEvent findById(Long id) {
		return actionEventDao.findById(id).get();
	}

	@Override
	public ActionEvent save(ActionEvent actionEvent) throws AlreadyExistsException {
		if(actionEventExist(actionEvent.getActionName()))
		{
			throw new AlreadyExistsException(
		              "There is an ActionEvent with the same name "
		              +  actionEvent.getActionName() );
		}
		return actionEventDao.save(actionEvent);
	}

	@Override
	public ActionEvent update(ActionEvent actionEvent) {
		ActionEvent updateActionEvent =actionEventDao.findById(actionEvent.getId()).get();
				
		updateActionEvent.setActionName(actionEvent.getActionName());
		updateActionEvent.setActionDesc(actionEvent.getActionDesc());
					
        return actionEventDao.save(updateActionEvent);
	}
	
	private boolean actionEventExist(String actionEventName) {
        List<ActionEvent> actions = actionEventDao.findByActionName(actionEventName);
        if (actions != null && actions.size()!=0 ) {
            return true;
        }
        return false;
    }

}
