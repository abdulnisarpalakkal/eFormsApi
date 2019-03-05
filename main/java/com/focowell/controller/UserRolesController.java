package com.focowell.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.UserRoles;
import com.focowell.service.UserRolesService;
import com.focowell.service.UserService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/userRole")
public class UserRolesController {

    @Autowired
    private UserRolesService userRolesService;
    
   
    @RequestMapping(value="/userRoles", method = RequestMethod.POST)
    public UserRoles create(@RequestBody UserRoles userRoles) throws AlreadyExistsException{
    	
    	return userRolesService.save(userRoles);
    }
    @RequestMapping(value="/userRoles", method = RequestMethod.GET)
    public List<UserRoles> list(){
        return userRolesService.findAll();
    }

    @RequestMapping(value = "/userRoles/{id}", method = RequestMethod.GET)
    public UserRoles getOne(@PathVariable(value = "id") Long id){
        return userRolesService.findById(id);
    }
    @RequestMapping(value = "/userRoles/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	userRolesService.delete(id);
        
        
    }
    @RequestMapping(value="/userRoles", method = RequestMethod.PUT)
    public UserRoles update(@RequestBody UserRoles userRoles) {
    	return userRolesService.update(userRoles);
    }
    
}
