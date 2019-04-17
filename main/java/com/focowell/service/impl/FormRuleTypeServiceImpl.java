package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.FormRuleTypeDao;
import com.focowell.model.FormRuleType;
import com.focowell.service.FormRuleTypeService;

@Service(value = "formRuleTypeService")
public class FormRuleTypeServiceImpl implements FormRuleTypeService {

	@Autowired
	private FormRuleTypeDao formRuleTypeDao;
	
	@Override
	public List<FormRuleType> findAll() {
		List<FormRuleType> list = new ArrayList<>();
		formRuleTypeDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		formRuleTypeDao.deleteById(id);
		
	}

	@Override
	public FormRuleType findOne(String formRuleTypeName) {
		return formRuleTypeDao.findByRuleTypeName(formRuleTypeName).get();
	}

	@Override
	public FormRuleType findById(Long id) {
		return formRuleTypeDao.findById(id).get();
	}

	@Override
	public FormRuleType save(FormRuleType formRuleType) throws AlreadyExistsException {
		if(formRuleTypeExist(formRuleType.getRuleTypeName()))
		{
			throw new AlreadyExistsException(
		              "There is an FormRuleType with the same name "
		              +  formRuleType.getRuleTypeName() );
		}
		return formRuleTypeDao.save(formRuleType);
	}

	@Override
	public FormRuleType update(FormRuleType formRuleType) {
		FormRuleType updateFormRuleType =formRuleTypeDao.findById(formRuleType.getId()).get();
				
//		updateFormRuleType.setFormRuleTypeName(formRuleType.());
//		updateFormRuleType.setFormRuleTypeDesc(formRuleType.getFormRuleTypeDesc());
					
        return formRuleTypeDao.save(updateFormRuleType);
	}
	
	private boolean formRuleTypeExist(String formRuleTypeName) {
       
        if (formRuleTypeDao.findByRuleTypeName(formRuleTypeName).isPresent() ) {
            return true;
        }
        return false;
    }

}
