package com.focowell.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.ActionEventParam;
import com.focowell.service.ActionEventParamService;
import com.focowell.service.UserService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/actionEventParam")
public class ActionEventParamController {

    @Autowired
    private ActionEventParamService actionEventParamService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/actionEventParams", method = RequestMethod.POST)
    public ActionEventParam create(@RequestBody ActionEventParam actionEventParam) throws AlreadyExistsException{
    	
    	return actionEventParamService.save(actionEventParam);
    }
    
    @RequestMapping(value="/actionEventParams", method = RequestMethod.GET)
    public List<ActionEventParam> list(){
        return actionEventParamService.findAll();
    }

    @RequestMapping(value = "/actionEventParams/{id}", method = RequestMethod.GET)
    public ActionEventParam getOne(@PathVariable(value = "id") Long id){
        return actionEventParamService.findById(id);
    }
    @RequestMapping(value = "/actionEventParams/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	actionEventParamService.delete(id);
        
        
    }
    @RequestMapping(value="/actionEventParams", method = RequestMethod.PUT)
    public ActionEventParam update(@RequestBody ActionEventParam actionEventParam) {
    	return actionEventParamService.update(actionEventParam);
    }
    
}
