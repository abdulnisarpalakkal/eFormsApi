package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.FormRuleTypeParameter;

public interface FormRuleTypeParameterService {
	    List<FormRuleTypeParameter> findAll();
	    void delete(long id);
	    FormRuleTypeParameter findOne(String formRuleTypeParameterName);

	    FormRuleTypeParameter findById(Long id);
	    FormRuleTypeParameter save(FormRuleTypeParameter formRuleTypeParameter) throws AlreadyExistsException;
	    FormRuleTypeParameter update(FormRuleTypeParameter formRuleTypeParameter);
}
