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
import com.focowell.model.WorkflowTrackDet;
import com.focowell.service.WorkflowTrackDetService;
import com.focowell.service.UserService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/workflowTrackDet")
public class WorkflowTrackDetController {

    @Autowired
    private WorkflowTrackDetService workflowTrackDetService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/workflowTrackDets", method = RequestMethod.POST)
    public WorkflowTrackDet create(@RequestBody WorkflowTrackDet workflowTrackDet) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
//    	workflowTrackDet.setCreatedUser(userService.findOne(auth.getName()));
    	return workflowTrackDetService.save(workflowTrackDet);
    }
    @RequestMapping(value="/workflowTrackDets", method = RequestMethod.GET)
    public List<WorkflowTrackDet> list(){
        return workflowTrackDetService.findAll();
    }
    @RequestMapping(value="/workflowTrackDets/user", method = RequestMethod.GET)
    public List<WorkflowTrackDet> listByUser(){
    	
        return workflowTrackDetService.findAllByUser();
    }
    @RequestMapping(value="/workflowTrackDets/process/{processId}", method = RequestMethod.GET)
    public List<WorkflowTrackDet> listByProcess(@PathVariable(value = "processId") Long processId){
    	
        return workflowTrackDetService.findAllByProcess(processId);
    }

    @RequestMapping(value = "/workflowTrackDets/{id}", method = RequestMethod.GET)
    public WorkflowTrackDet getOne(@PathVariable(value = "id") Long id){
        return workflowTrackDetService.findById(id);
    }
    @RequestMapping(value = "/workflowTrackDets/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	workflowTrackDetService.delete(id);
        
        
    }
    @RequestMapping(value="/workflowTrackDets", method = RequestMethod.PUT)
    public WorkflowTrackDet update(@RequestBody WorkflowTrackDet workflowTrackDet) {
    	return workflowTrackDetService.update(workflowTrackDet);
    }
    
}
