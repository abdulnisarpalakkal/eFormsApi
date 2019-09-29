package com.focowell.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.focowell.model.VirtualRowRecord;
import com.focowell.service.VirtualTableRecordsMongoService;

@RestController
@RequestMapping("/virtualRowRecord")
public class VirtualTableRecordsMongoController {

	@Autowired
    private VirtualTableRecordsMongoService virtualTableRecordsMongoService;
	
	 @RequestMapping(value="/virtualRowRecords", method = RequestMethod.GET)
	    public List<VirtualRowRecord> list(){
	        return virtualTableRecordsMongoService.findAll();
	    }
}
