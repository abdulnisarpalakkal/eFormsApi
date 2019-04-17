package com.focowell.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.FormDesign;
import com.focowell.model.FormMaster;
import com.focowell.model.dto.FormDesignDto;
import com.focowell.service.UserService;
import com.focowell.service.FormMasterService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
//@RequestMapping("/formMaster")
public class FormMasterController {

    @Autowired
    private FormMasterService formMasterService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/formMaster", method = RequestMethod.POST)
    public FormMaster create(@RequestBody FormMaster formMaster) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	return formMasterService.save(formMaster);
    }
    
    @RequestMapping(value="/formMaster", method = RequestMethod.GET)
    public List<FormMaster> list(){
        return formMasterService.findAll();
    }
    
    @RequestMapping(value="/formMaster/process/{processId}", method = RequestMethod.GET)
    public List<FormMaster> listAllByProcess(@PathVariable(value = "processId") Long processId){
        return formMasterService.findAllByProcess(processId);
    }

    @RequestMapping(value = "/formMaster/{id}", method = RequestMethod.GET)
    public FormMaster getOne(@PathVariable(value = "id") Long id){
        return formMasterService.findById(id);
    }
    @RequestMapping(value = "/formMaster/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	formMasterService.delete(id);
        
        
    }
    @RequestMapping(value="/formMaster", method = RequestMethod.PUT)
    public FormMaster update(@RequestBody FormMaster formMaster) throws AlreadyExistsException {
    	return formMasterService.update(formMaster);
    }
    @RequestMapping(value="/formMaster/design", method = RequestMethod.PUT)
    public boolean updateDesign(@RequestBody FormDesignDto formDesignDto)  {
    	return formMasterService.updateDesign(formDesignDto);
    }
    
}
