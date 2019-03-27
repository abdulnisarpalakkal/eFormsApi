package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.ProcessData;
import com.focowell.model.dto.ProcessSubModulesDto;
import com.focowell.service.ProcessService;
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

import javax.validation.Valid;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;
    
    @Autowired
    private UserService userService;

    @RequestMapping( method = RequestMethod.POST)
    public ProcessData create(@Valid @RequestBody ProcessData process) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
    	process.setCreatedUser(userService.findOne(auth.getName()));
    	return processService.save(process);
    }
    @RequestMapping( method = RequestMethod.GET)
    public List<ProcessData> list(){
        return processService.findAll();
    }
    @RequestMapping(value="/submodules/{processId}", method = RequestMethod.GET)
    public ProcessSubModulesDto listAllSubModules(@PathVariable(value = "processId") Long processId){
        return processService.findAllSubModules(processId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProcessData getOne(@PathVariable(value = "id") Long id){
        return processService.findById(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	processService.delete(id);
        
        
    }
    @RequestMapping( method = RequestMethod.PUT)
    public ProcessData update(@Valid @RequestBody ProcessData process) throws AlreadyExistsException {
    	return processService.update(process);
    }
    
}
