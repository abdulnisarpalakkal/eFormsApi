package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.ActionEvent;

public interface ActionEventService {
	    List<ActionEvent> findAll();
	    void delete(long id);
	    ActionEvent findOne(String actionEventName);

	    ActionEvent findById(Long id);
	    ActionEvent save(ActionEvent actionEvent) throws AlreadyExistsException;
	    ActionEvent update(ActionEvent actionEvent);
}
