package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.ActionEventParamDao;
import com.focowell.dao.UserDao;
import com.focowell.model.ActionEventParam;
import com.focowell.model.User;
import com.focowell.service.ActionEventParamService;

@Service(value = "actionEventParamService")
public class ActionEventParamServiceImpl implements ActionEventParamService {

	@Autowired
	private ActionEventParamDao actionEventParamDao;
	
	@Override
	public List<ActionEventParam> findAll() {
		List<ActionEventParam> list = new ArrayList<>();
		actionEventParamDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		actionEventParamDao.deleteById(id);
		
	}

	@Override
	public ActionEventParam findOne(String actionEventParamName) {
		return actionEventParamDao.findByParamName(actionEventParamName).get(0);
	}

	@Override
	public ActionEventParam findById(Long id) {
		return actionEventParamDao.findById(id).get();
	}

	@Override
	public ActionEventParam save(ActionEventParam actionEventParam) throws AlreadyExistsException {
		if(actionEventParamExist(actionEventParam.getParamName()))
		{
			throw new AlreadyExistsException(
		              "There is an parameter with the same name "
		              +  actionEventParam.getParamName() );
		}
		return actionEventParamDao.save(actionEventParam);
	}

	@Override
	public ActionEventParam update(ActionEventParam actionEventParam) {
		ActionEventParam updateActionEventParam =actionEventParamDao.findById(actionEventParam.getId()).get();
				
		updateActionEventParam.setParamName(actionEventParam.getParamName());
		updateActionEventParam.setActionEvent(actionEventParam.getActionEvent());
					
        return actionEventParamDao.save(updateActionEventParam);
	}
	
	private boolean actionEventParamExist(String actionEventParamName) {
        List<ActionEventParam> prams = actionEventParamDao.findByParamName(actionEventParamName);
        if (prams != null && prams.size()!=0 ) {
            return true;
        }
        return false;
    }

}
