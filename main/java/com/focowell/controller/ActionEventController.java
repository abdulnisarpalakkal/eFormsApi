package com.focowell.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.ActionEvent;
import com.focowell.service.ActionEventService;
import com.focowell.service.UserService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/actionEvent")
public class ActionEventController {

    @Autowired
    private ActionEventService actionEventService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/actionEvents", method = RequestMethod.POST)
    public ActionEvent create(@RequestBody ActionEvent actionEvent) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
//    	actionEvent.setCreatedUser(userService.findOne(auth.getName()));
    	return actionEventService.save(actionEvent);
    }
    @RequestMapping(value="/actionEvents", method = RequestMethod.GET)
    public List<ActionEvent> list(){
        return actionEventService.findAll();
    }

    @RequestMapping(value = "/actionEvents/{id}", method = RequestMethod.GET)
    public ActionEvent getOne(@PathVariable(value = "id") Long id){
        return actionEventService.findById(id);
    }
    @RequestMapping(value = "/actionEvents/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	actionEventService.delete(id);
        
        
    }
    @RequestMapping(value="/actionEvents", method = RequestMethod.PUT)
    public ActionEvent update(@RequestBody ActionEvent actionEvent) {
    	return actionEventService.update(actionEvent);
    }
    
}
