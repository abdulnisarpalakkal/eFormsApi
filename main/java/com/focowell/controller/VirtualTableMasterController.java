package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.VirtualTableMaster;
import com.focowell.model.dto.UserDto;
import com.focowell.model.dto.VirtualTableFKConstraintRefDto;
import com.focowell.model.dto.VirtualTableFieldsConstraintDto;
import com.focowell.model.dto.VirtualTableRecordForGridDto;
import com.focowell.model.User;
import com.focowell.model.VirtualTableField;
import com.focowell.service.VirtualTableMasterService;
import com.focowell.service.VirtualTableRecordsService;
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
import java.util.Map;
import java.util.Set;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
//@RequestMapping("/virtualTableMaster")
public class VirtualTableMasterController {

    @Autowired
    private VirtualTableMasterService virtualTableMasterService;
    @Autowired
    private VirtualTableRecordsService virtualTableRecordsService;
    
    @Autowired
    private UserService userService;

//    @RequestMapping(value="/virtualTableMaster", method = RequestMethod.POST)
//    public VirtualTableMaster create(@RequestBody VirtualTableMaster virtualTableMaster) throws AlreadyExistsException{
//    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
//    	
//    	virtualTableMaster.setCreatedUser(userService.findOne(auth.getName()));
////    	virtualTableMaster.setVirtualTableFieldsList(virtualTableFieldList);
//    	return virtualTableMasterService.save(virtualTableMaster);
//    }
    
    @RequestMapping(value="/virtualTableMaster", method = RequestMethod.POST)
    public VirtualTableMaster create(@RequestBody VirtualTableFieldsConstraintDto virtualTableFieldsConstraintDto) throws AlreadyExistsException{
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    	
    	virtualTableFieldsConstraintDto.getVirtualTable().setCreatedUser(userService.findOne(auth.getName()));
//    	virtualTableMaster.setVirtualTableFieldsList(virtualTableFieldList);
    	return virtualTableMasterService.save(virtualTableFieldsConstraintDto);
    }
    @RequestMapping(value="/virtualTableMaster", method = RequestMethod.GET)
    public List<VirtualTableMaster> list(){
        return virtualTableMasterService.findAll();
    }
   
    @RequestMapping(value="/virtualTableMaster/process/{processId}", method = RequestMethod.GET)
    public List<VirtualTableMaster> listByProcess(@PathVariable(value = "processId") Long processId){
        return virtualTableMasterService.findAllByProcessId(processId);
    }

    @RequestMapping(value = "/virtualTableMaster/{id}", method = RequestMethod.GET)
    public VirtualTableMaster getOne(@PathVariable(value = "id") Long id){
        return virtualTableMasterService.findById(id);
    }
    @RequestMapping(value = "/virtualTableMaster/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
    	virtualTableMasterService.delete(id);
        
        
    }
    @RequestMapping(value="/virtualTableMaster", method = RequestMethod.PUT)
    public VirtualTableMaster update(@RequestBody VirtualTableFieldsConstraintDto virtualTableFieldsConstraintDto) throws AlreadyExistsException {
    	return virtualTableMasterService.update(virtualTableFieldsConstraintDto);
    }
    
    @RequestMapping(value="/virtualTableMaster/constraintRef/{id}", method = RequestMethod.GET)
    public VirtualTableFKConstraintRefDto getConstraintRef(@PathVariable(value = "id") long processId)  {
    	return virtualTableMasterService.findVirtualTableConstraintRefData(processId);
    }
    @RequestMapping(value="/virtualTableMaster/records/{tableId}", method = RequestMethod.GET)
    public VirtualTableRecordForGridDto listRecordsByTable(@PathVariable(value = "tableId") long tableId){
        return virtualTableRecordsService.findAllByTableForGrid(tableId);
    }
    
}
