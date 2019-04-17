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
import com.focowell.model.FormComponentType;
import com.focowell.model.FormDesign;
import com.focowell.model.VirtualTableFieldDataType;
import com.focowell.model.dto.FormDesignDto;
import com.focowell.service.UserService;
import com.focowell.service.FormDesignService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
//@RequestMapping("/formDesign")
public class FormDesignController {

    @Autowired
    private FormDesignService formDesignService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/formDesign", method = RequestMethod.POST)
    public FormDesign create(@RequestBody FormDesign formDesign) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	return formDesignService.save(formDesign);
    }
    
    @RequestMapping(value="/formDesign/all", method = RequestMethod.POST)
    public List<FormDesign> createAll(@RequestBody List<FormDesign> formDesigns) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	return formDesignService.saveAll(formDesigns);
    }
    
    @RequestMapping(value="/formDesign", method = RequestMethod.GET)
    public List<FormDesign> list(){
        return formDesignService.findAll();
    }
    @RequestMapping(value="/formDesign/form/{formId}", method = RequestMethod.GET)
    public FormDesignDto listByFormId(@PathVariable(value = "formId") Long formId){
        return formDesignService.findAllByFormId(formId);
    }
    @RequestMapping(value="/formDesign/components/form/{formId}", method = RequestMethod.GET)
    public List<FormDesign> listComponentsByFormId(@PathVariable(value = "formId") Long formId){
        return formDesignService.findAllFormComponentsByFormId(formId);
    }
    @RequestMapping(value = "/formDesign/{id}", method = RequestMethod.GET)
    public FormDesign getOne(@PathVariable(value = "id") Long id){
        return formDesignService.findById(id);
    }
    @RequestMapping(value = "/formDesign/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	formDesignService.delete(id);
        
    }
    @RequestMapping(value="/formDesign", method = RequestMethod.PUT)
    public FormDesign update(@RequestBody FormDesign formDesign) throws AlreadyExistsException {
    	return formDesignService.update(formDesign);
    }
    @RequestMapping(value="/formDesign/types", method = RequestMethod.GET)
    public List<FormComponentType> listTypes(){
        return formDesignService.findAllFormComponentTypes();
    }
}
