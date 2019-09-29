package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.UserDto;
import com.focowell.model.FormRuleType;
import com.focowell.model.User;
import com.focowell.service.FormRuleTypeService;
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
@RequestMapping("/formRuleType")
public class FormRuleTypeController {

    @Autowired
    private FormRuleTypeService formRuleTypeService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/formRuleTypes", method = RequestMethod.POST)
    public FormRuleType create(@RequestBody FormRuleType formRuleType) throws AlreadyExistsException{
    	
    	return formRuleTypeService.save(formRuleType);
    }
    @RequestMapping(value="/formRuleTypes", method = RequestMethod.GET)
    public List<FormRuleType> list(){
        return formRuleTypeService.findAll();
    }

    @RequestMapping(value = "/formRuleTypes/{id}", method = RequestMethod.GET)
    public FormRuleType getOne(@PathVariable(value = "id") Long id){
        return formRuleTypeService.findById(id);
    }
    @RequestMapping(value = "/formRuleTypes/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	formRuleTypeService.delete(id);
        
        
    }
    @RequestMapping(value="/formRuleTypes", method = RequestMethod.PUT)
    public FormRuleType update(@RequestBody FormRuleType formRuleType) {
    	return formRuleTypeService.update(formRuleType);
    }
    
}
