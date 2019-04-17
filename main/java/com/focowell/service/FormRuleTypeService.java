package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.FormRuleType;

public interface FormRuleTypeService {
	    List<FormRuleType> findAll();
	    void delete(long id);
	    FormRuleType findOne(String formRuleTypeName);

	    FormRuleType findById(Long id);
	    FormRuleType save(FormRuleType formRuleType) throws AlreadyExistsException;
	    FormRuleType update(FormRuleType formRuleType);
}
