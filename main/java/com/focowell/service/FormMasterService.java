package com.focowell.service;

import java.util.List;
import java.util.Set;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.FormDesignPageDto;
import com.focowell.model.FormDesign;
import com.focowell.model.FormMaster;

public interface FormMasterService {
	    List<FormMaster> findAll();
	    List<FormMaster> findAllByProcess(long processId);
	    void delete(long id);
	    FormMaster findOne(String formMasterName);

	    FormMaster findById(Long id);
	    FormMaster save(FormMaster formMaster) throws AlreadyExistsException;
	    FormMaster update(FormMaster formMaster) throws AlreadyExistsException;
		boolean updateDesign(FormDesignPageDto formDesignDto);
}
