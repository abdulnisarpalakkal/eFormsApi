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
import com.focowell.model.VirtualTableConstraints;
import com.focowell.service.UserService;
import com.focowell.service.VirtualTableConstraintsService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
//@RequestMapping("/virtualTableConstraints")
public class VirtualTableConstraintsController {

    @Autowired
    private VirtualTableConstraintsService virtualTableConstraintsService;
   
    @RequestMapping(value="/virtualTableConstraints", method = RequestMethod.POST)
    public VirtualTableConstraints create(@RequestBody VirtualTableConstraints virtualTableConstraints) throws AlreadyExistsException{
    	
    	return virtualTableConstraintsService.save(virtualTableConstraints);
    }
    @RequestMapping(value="/virtualTableConstraints/table/{id}", method = RequestMethod.GET)
    public List<VirtualTableConstraints> list(@PathVariable(value = "id") Long id){
        return virtualTableConstraintsService.findAll(id);
    }

    @RequestMapping(value = "/virtualTableConstraints/{id}", method = RequestMethod.GET)
    public VirtualTableConstraints getOne(@PathVariable(value = "id") Long id){
        return virtualTableConstraintsService.findById(id);
    }
    @RequestMapping(value = "/virtualTableConstraints/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	virtualTableConstraintsService.delete(id);
        
        
    }
   
    
}
