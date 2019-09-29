package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.UserDto;
import com.focowell.model.User;
import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowMaster;
import com.focowell.model.WorkflowNode;
import com.focowell.service.WorkflowNodeService;
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
@RequestMapping("/workflowNode")
public class WorkflowNodeController {

    @Autowired
    private WorkflowNodeService workflowNodeService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/workflowNodes", method = RequestMethod.POST)
    public WorkflowNode create(@RequestBody WorkflowNode workflowNode) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
//    	workflowNode.setCreatedUser(userService.findOne(auth.getName()));
    	return workflowNodeService.save(workflowNode);
    }
    @RequestMapping(value="/workflowNodes/all", method = RequestMethod.POST)
    public boolean createAll(@RequestBody WorkflowMaster workflowMaster) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
//    	workflowNode.setCreatedUser(userService.findOne(auth.getName()));
    	return workflowNodeService.saveAll(workflowMaster);
    }
    @RequestMapping(value="/workflowNodes", method = RequestMethod.GET)
    public List<WorkflowNode> list(){
        return workflowNodeService.findAll();
    }
    @RequestMapping(value="/workflowNodes/workflow/{workflowId}", method = RequestMethod.GET)
    public List<WorkflowNode> list(@PathVariable(value = "workflowId") Long workflowId){
        return workflowNodeService.findAllByWorkflow(workflowId);
    }

    @RequestMapping(value = "/workflowNodes/{id}", method = RequestMethod.GET)
    public WorkflowNode getOne(@PathVariable(value = "id") Long id){
        return workflowNodeService.findById(id);
    }
    @RequestMapping(value = "/workflowNodes/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	workflowNodeService.delete(id);
        
        
    }
    @RequestMapping(value="/workflowNodes", method = RequestMethod.PUT)
    public WorkflowNode update(@RequestBody WorkflowNode workflowNode) {
    	return workflowNodeService.update(workflowNode);
    }
    
}
