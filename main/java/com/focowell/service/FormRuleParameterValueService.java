package com.focowell.service;

import java.util.List;

import com.focowell.model.FormRuleParameterValue;

public interface FormRuleParameterValueService {
	    List<FormRuleParameterValue> findAll();
	    void delete(long id);
	    FormRuleParameterValue findOne(String formRuleParameterValueName);

	    FormRuleParameterValue findById(Long id);
	    FormRuleParameterValue save(FormRuleParameterValue formRuleParameterValue) ;
	    FormRuleParameterValue update(FormRuleParameterValue formRuleParameterValue);
}
