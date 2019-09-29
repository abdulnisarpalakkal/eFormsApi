package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.FormDesignPageDto;
import com.focowell.model.FormComponentType;
import com.focowell.model.FormDesign;
import com.focowell.model.VirtualTableField;

public interface FormDesignService {
	    List<FormDesign> findAll();
	    FormDesignPageDto findAllByFormId(Long tableId);
	    void delete(long id);
	    FormDesign findOne(String componentName);

	    FormDesign findById(Long id);
	    FormDesign save(FormDesign formDesign) throws AlreadyExistsException;
	    List<FormDesign> saveAll(List<FormDesign> formDesigns) ;
	    FormDesign update(FormDesign formDesign) throws AlreadyExistsException;
		List<FormComponentType> findAllFormComponentTypes();
		List<FormDesign> findAllFormComponentsByFormId(Long formId);
		void populateRefValuesIfForeignKey(FormDesign formDesign);
		List<FormDesign> getFormDesignFromTableFields(List<VirtualTableField> fields);
}
