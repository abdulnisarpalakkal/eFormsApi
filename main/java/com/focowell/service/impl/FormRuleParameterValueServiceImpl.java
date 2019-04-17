package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.dao.FormRuleParameterValueDao;
import com.focowell.model.FormRuleParameterValue;
import com.focowell.service.FormRuleParameterValueService;

@Service(value = "formRuleParameterValueService")
public class FormRuleParameterValueServiceImpl implements FormRuleParameterValueService {

	@Autowired
	private FormRuleParameterValueDao formRuleParameterValueDao;
	
	@Override
	public List<FormRuleParameterValue> findAll() {
		List<FormRuleParameterValue> list = new ArrayList<>();
		formRuleParameterValueDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		formRuleParameterValueDao.deleteById(id);
		
	}

	@Override
	public FormRuleParameterValue findOne(String parameterName) {
		return formRuleParameterValueDao.findByFormRuleTypeParameter_ParameterName(parameterName).get();
	}

	@Override
	public FormRuleParameterValue findById(Long id) {
		return formRuleParameterValueDao.findById(id).get();
	}

	@Override
	public FormRuleParameterValue save(FormRuleParameterValue formRuleParameterValue) {
		
		return formRuleParameterValueDao.save(formRuleParameterValue);
	}

	@Override
	public FormRuleParameterValue update(FormRuleParameterValue formRuleParameterValue) {
		FormRuleParameterValue updateFormRuleParameterValue =formRuleParameterValueDao.findById(formRuleParameterValue.getId()).get();
				
		
					
        return formRuleParameterValueDao.save(updateFormRuleParameterValue);
	}
	
	

}
