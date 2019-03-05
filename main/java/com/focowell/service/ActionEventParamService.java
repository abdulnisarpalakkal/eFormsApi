package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.ActionEventParam;

public interface ActionEventParamService {
	    List<ActionEventParam> findAll();
	    void delete(long id);
	    ActionEventParam findOne(String actionEventParamName);

	    ActionEventParam findById(Long id);
	    ActionEventParam save(ActionEventParam actionEventParam) throws AlreadyExistsException;
	    ActionEventParam update(ActionEventParam actionEventParam);
}
