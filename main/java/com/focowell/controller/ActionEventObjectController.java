package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.ActionEventObject;
import com.focowell.model.User;
import com.focowell.model.dto.UserDto;
import com.focowell.service.ActionEventObjectService;
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
@RequestMapping("/actionEventObject")
public class ActionEventObjectController {

    @Autowired
    private ActionEventObjectService actionEventObjectService;
  
    @RequestMapping(value="/actionEventObjects", method = RequestMethod.POST)
    public ActionEventObject create(@RequestBody ActionEventObject actionEventObject) throws AlreadyExistsException{
    	
    	return actionEventObjectService.save(actionEventObject);
    }
    @RequestMapping(value="/actionEventObjects", method = RequestMethod.GET)
    public List<ActionEventObject> list(){
        return actionEventObjectService.findAll();
    }

    @RequestMapping(value = "/actionEventObjects/{id}", method = RequestMethod.GET)
    public ActionEventObject getOne(@PathVariable(value = "id") Long id){
        return actionEventObjectService.findById(id);
    }
    @RequestMapping(value = "/actionEventObjects/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	actionEventObjectService.delete(id);
        
        
    }
    @RequestMapping(value="/actionEventObjects", method = RequestMethod.PUT)
    public ActionEventObject update(@RequestBody ActionEventObject actionEventObject) {
    	return actionEventObjectService.update(actionEventObject);
    }
    
}
