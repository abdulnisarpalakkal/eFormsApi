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
import com.focowell.model.WorkflowMaster;
import com.focowell.service.WorkflowMasterService;
import com.focowell.service.UserService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/workflow")
public class WorkflowMasterController {

    @Autowired
    private WorkflowMasterService workflowMasterService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/workflows", method = RequestMethod.POST)
    public WorkflowMaster create(@RequestBody WorkflowMaster workflowMaster) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
//    	workflowMaster.setCreatedUser(userService.findOne(auth.getName()));
    	return workflowMasterService.save(workflowMaster);
    }
    @RequestMapping(value="/workflows/design", method = RequestMethod.POST)
    public void createDesign(@RequestBody WorkflowMaster workflowMaster) throws AlreadyExistsException{
    
    	workflowMasterService.saveDesign(workflowMaster);
    }
    @RequestMapping(value="/workflows", method = RequestMethod.GET)
    public List<WorkflowMaster> list(){
        return workflowMasterService.findAll();
    }

    @RequestMapping(value="/workflows/process/{processId}", method = RequestMethod.GET)
    public List<WorkflowMaster> listByProcess(@PathVariable(value = "processId") Long processId){
        return workflowMasterService.findAllByProcess(processId);
    }

    @RequestMapping(value="/workflows/published", method = RequestMethod.GET)
    public List<WorkflowMaster> listPublished(){
        return workflowMasterService.findAllPublished();
    }
    @RequestMapping(value="/workflows/published/child/{processId}", method = RequestMethod.GET)
    public List<WorkflowMaster> listAllPublishedChildWorkflowsByProcess(@PathVariable(value = "processId") Long processId){
        return workflowMasterService.findAllPublishedChildWorkflowsByProcess(processId);
    }
    @RequestMapping(value = "/workflows/{id}", method = RequestMethod.GET)
    public WorkflowMaster getOne(@PathVariable(value = "id") Long id){
        return workflowMasterService.findById(id);
    }
    @RequestMapping(value = "/workflows/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	workflowMasterService.delete(id);
        
        
    }
    @RequestMapping(value="/workflows", method = RequestMethod.PUT)
    public WorkflowMaster update(@RequestBody WorkflowMaster workflowMaster) {
    	return workflowMasterService.update(workflowMaster);
    }
    @RequestMapping(value="/workflows/publish", method = RequestMethod.PUT)
    public WorkflowMaster publish(@RequestBody WorkflowMaster workflowMaster) {
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	workflowMaster.setPublishUser(userService.findOne(auth.getName()));
    	return workflowMasterService.publishWorkflow(workflowMaster);
    }
    
}
