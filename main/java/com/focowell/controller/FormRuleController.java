package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.FormRule;
import com.focowell.model.User;
import com.focowell.model.dto.UserDto;
import com.focowell.service.FormRuleService;
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
@RequestMapping("/formRule")
public class FormRuleController {

    @Autowired
    private FormRuleService formRuleService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/formRules", method = RequestMethod.POST)
    public FormRule create(@RequestBody FormRule formRule) throws AlreadyExistsException{
    	
    	return formRuleService.save(formRule);
    }
    @RequestMapping(value="/formRules", method = RequestMethod.GET)
    public List<FormRule> list(){
        return formRuleService.findAll();
    }

    @RequestMapping(value = "/formRules/{id}", method = RequestMethod.GET)
    public FormRule getOne(@PathVariable(value = "id") Long id){
        return formRuleService.findById(id);
    }
    @RequestMapping(value = "/formRules/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	formRuleService.delete(id);
        
        
    }
    @RequestMapping(value="/formRules", method = RequestMethod.PUT)
    public FormRule update(@RequestBody FormRule formRule) {
    	return formRuleService.update(formRule);
    }
    
}
