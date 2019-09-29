package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.UserDto;
import com.focowell.model.User;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableFieldDataType;
import com.focowell.service.VirtualTableFieldsService;
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
//@RequestMapping("/virtualTableFields")
public class VirtualTableFieldsController {

    @Autowired
    private VirtualTableFieldsService virtualTableFieldsService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/virtualTableFields", method = RequestMethod.POST)
    public VirtualTableField create(@RequestBody VirtualTableField virtualTableFields) throws AlreadyExistsException{
//    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
//    	
//    	virtualTableFields.set(userService.findOne(auth.getName()));
    	return virtualTableFieldsService.save(virtualTableFields);
    }
    @RequestMapping(value="/virtualTableFields", method = RequestMethod.GET)
    public List<VirtualTableField> list(){
        return virtualTableFieldsService.findAll();
    }
    @RequestMapping(value="/virtualTableFields/table/{id}", method = RequestMethod.GET)
    public List<VirtualTableField> listByTable(@PathVariable(value = "id") Long id){
        return virtualTableFieldsService.findAllByTableId(id);
    }
    
    @RequestMapping(value="/virtualTableFields/table/fieldNames/{id}", method = RequestMethod.GET)
    public List<String> fieldNamesListByTable(@PathVariable(value = "id") Long id){
        return virtualTableFieldsService.findAllFieldNamesByTableId(id);
    }
    
    @RequestMapping(value = "/virtualTableFields/{id}", method = RequestMethod.GET)
    public VirtualTableField getOne(@PathVariable(value = "id") Long id){
        return virtualTableFieldsService.findById(id);
    }
    @RequestMapping(value = "/virtualTableFields/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	virtualTableFieldsService.delete(id);
        
        
    }
    @RequestMapping(value="/virtualTableFields", method = RequestMethod.PUT)
    public VirtualTableField update(@RequestBody VirtualTableField virtualTableFields) {
    	return virtualTableFieldsService.update(virtualTableFields);
    }
    @RequestMapping(value="/virtualTableFields/types", method = RequestMethod.GET)
    public List<VirtualTableFieldDataType> listTypes(){
        return virtualTableFieldsService.findAllVirtualTableFieldDataTypes();
    }

}
