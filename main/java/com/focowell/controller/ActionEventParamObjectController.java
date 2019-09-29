package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.UserDto;
import com.focowell.model.ActionEventParamObject;
import com.focowell.model.User;
import com.focowell.service.ActionEventParamObjectService;
import com.focowell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/actionEventParamObject")
public class ActionEventParamObjectController {

    @Autowired
    private ActionEventParamObjectService actionEventParamObjectService;
    
 
    @RequestMapping(value="/actionEventParamObjects", method = RequestMethod.POST)
    public ActionEventParamObject create(@RequestBody ActionEventParamObject actionEventParamObject) throws AlreadyExistsException{
    	
    	return actionEventParamObjectService.save(actionEventParamObject);
    }
    @RequestMapping(value="/actionEventParamObjects", method = RequestMethod.GET)
    public List<ActionEventParamObject> list(){
        return actionEventParamObjectService.findAll();
    }

    @RequestMapping(value = "/actionEventParamObjects/{id}", method = RequestMethod.GET)
    public ActionEventParamObject getOne(@PathVariable(value = "id") Long id){
        return actionEventParamObjectService.findById(id);
    }
    @RequestMapping(value = "/actionEventParamObjects/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	actionEventParamObjectService.delete(id);
                
    }
    
    @RequestMapping(value="/actionEventParamObjects", method = RequestMethod.PUT)
    public ActionEventParamObject update(@RequestBody ActionEventParamObject actionEventParamObject) {
    	return actionEventParamObjectService.update(actionEventParamObject);
    }
    
}
