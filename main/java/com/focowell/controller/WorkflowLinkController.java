package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.WorkflowLink;
import com.focowell.model.dto.UserDto;
import com.focowell.model.User;
import com.focowell.service.WorkflowLinkService;
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
@RequestMapping("/workflowLink")
public class WorkflowLinkController {

    @Autowired
    private WorkflowLinkService workflowLinkService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/workflowLinks", method = RequestMethod.POST)
    public WorkflowLink create(@RequestBody WorkflowLink workflowLink) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
//    	workflowLink.setCreatedUser(userService.findOne(auth.getName()));
    	return workflowLinkService.save(workflowLink);
    }
    @RequestMapping(value="/workflowLinks/all", method = RequestMethod.POST)
    public boolean createAll(@RequestBody List<WorkflowLink> workflowLinks) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
//    	workflowLink.setCreatedUser(userService.findOne(auth.getName()));
    	return workflowLinkService.saveAll(workflowLinks);
    }
    
    @RequestMapping(value="/workflowLinks", method = RequestMethod.GET)
    public List<WorkflowLink> list(){
        return workflowLinkService.findAll();
    }
    @RequestMapping(value="/workflowLinks/workflow/{workflowId}", method = RequestMethod.GET)
    public List<WorkflowLink> list(@PathVariable(value = "workflowId") Long workflowId){
        return workflowLinkService.findAllByWorkflow(workflowId);
    }

    @RequestMapping(value = "/workflowLinks/{id}", method = RequestMethod.GET)
    public WorkflowLink getOne(@PathVariable(value = "id") Long id){
        return workflowLinkService.findById(id);
    }
    @RequestMapping(value = "/workflowLinks/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	workflowLinkService.delete(id);
        
        
    }
    @RequestMapping(value="/workflowLinks", method = RequestMethod.PUT)
    public WorkflowLink update(@RequestBody WorkflowLink workflowLink) {
    	return workflowLinkService.update(workflowLink);
    }
    
}
