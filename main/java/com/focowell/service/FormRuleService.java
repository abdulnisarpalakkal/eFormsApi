package com.focowell.service;

import java.util.List;
import java.util.Set;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.FormRule;

public interface FormRuleService {
	    List<FormRule> findAll();
	    void delete(long id);
	    FormRule findOne(String formRuleName);

	    FormRule findById(Long id);
	    List<FormRule> findByFormId(Long formId);
	    FormRule save(FormRule formRule) throws AlreadyExistsException;
	    FormRule update(FormRule formRule);
		List<FormRule> saveAll(List<FormRule> formRules);
}
