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
import com.focowell.model.WorkflowStage;
import com.focowell.model.WorkflowTrackMaster;
import com.focowell.service.WorkflowTrackMasterService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/workflowTrackMaster")
public class WorkflowTrackMasterController {

    @Autowired
    private WorkflowTrackMasterService workflowTrackMasterService;
    
 

    @RequestMapping(value="/workflowTrackMasters", method = RequestMethod.POST)
    public WorkflowTrackMaster create(@RequestBody WorkflowTrackMaster workflowTrackMaster) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
//    	workflowTrackMaster.setCreatedUser(userService.findOne(auth.getName()));
    	return workflowTrackMasterService.save(workflowTrackMaster);
    }
    @RequestMapping(value="/workflowTrackMasters", method = RequestMethod.GET)
    public List<WorkflowTrackMaster> list(){
        return workflowTrackMasterService.findAll();
    }
    
    @RequestMapping(value="/workflowTrackMasters/workflow/{workflowId}", method = RequestMethod.GET)
    public List<WorkflowTrackMaster> listByWorkflow(@PathVariable(value = "workflowId") Long workflowId){
        return workflowTrackMasterService.findAllByWorkflow(workflowId);
    }
    
    @RequestMapping(value="/workflowTrackMasters/process/{processId}", method = RequestMethod.GET)
    public List<WorkflowTrackMaster> listByProcess(@PathVariable(value = "processId") Long processId){
        return workflowTrackMasterService.findAllByProcess(processId);
    }
    @RequestMapping(value="/workflowTrackMasters/user", method = RequestMethod.GET)
    public List<WorkflowTrackMaster> listByUser(){
        return workflowTrackMasterService.findAllByUser();
    }

    @RequestMapping(value = "/workflowTrackMasters/{id}", method = RequestMethod.GET)
    public WorkflowTrackMaster getOne(@PathVariable(value = "id") Long id){
        return workflowTrackMasterService.findById(id);
    }
    @RequestMapping(value = "/workflowTrackMasters/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	workflowTrackMasterService.delete(id);
        
        
    }
    @RequestMapping(value="/workflowTrackMasters", method = RequestMethod.PUT)
    public WorkflowTrackMaster update(@RequestBody WorkflowTrackMaster workflowTrackMaster) {
    	return workflowTrackMasterService.update(workflowTrackMaster);
    }
    @RequestMapping(value="/workflowTrackMasters/openWorkflows", method = RequestMethod.GET)
    public List<WorkflowStage> getOpenWorkflows() {
    	return workflowTrackMasterService.findAllOpenWorkflowTracks();
    }
    
    @RequestMapping(value="/workflowTrackMasters/execute", method = RequestMethod.POST)
    public WorkflowStage execute(@RequestBody WorkflowStage workflowStage) {
    	return workflowTrackMasterService.execute(workflowStage);
    }
    
    @RequestMapping(value="/workflowTrackMasters/action", method = RequestMethod.POST)
    public void submitAction(@RequestBody WorkflowStage workflowStage) throws Exception {
    	 workflowTrackMasterService.submitAction(workflowStage);
    }
   
}
