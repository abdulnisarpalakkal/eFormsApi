package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.FormRuleTypeParameterDao;
import com.focowell.dao.UserDao;
import com.focowell.model.FormRuleTypeParameter;
import com.focowell.model.User;
import com.focowell.service.FormRuleTypeParameterService;

@Service(value = "formRuleTypeParameterService")
public class FormRuleTypeParameterServiceImpl implements FormRuleTypeParameterService {

	@Autowired
	private FormRuleTypeParameterDao formRuleTypeParameterDao;
	
	@Override
	public List<FormRuleTypeParameter> findAll() {
		List<FormRuleTypeParameter> list = new ArrayList<>();
		formRuleTypeParameterDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		formRuleTypeParameterDao.deleteById(id);
		
	}

	@Override
	public FormRuleTypeParameter findOne(String formRuleTypeParameterName) {
		return formRuleTypeParameterDao.findByParameterName(formRuleTypeParameterName).get();
	}

	@Override
	public FormRuleTypeParameter findById(Long id) {
		return formRuleTypeParameterDao.findById(id).get();
	}

	@Override
	public FormRuleTypeParameter save(FormRuleTypeParameter formRuleTypeParameter) throws AlreadyExistsException {
		if(formRuleTypeParameterExist(formRuleTypeParameter.getParameterName()))
		{
			throw new AlreadyExistsException(
		              "There is an FormRuleTypeParameter with the same name "
		              +  formRuleTypeParameter.getParameterName() );
		}
		return formRuleTypeParameterDao.save(formRuleTypeParameter);
	}

	@Override
	public FormRuleTypeParameter update(FormRuleTypeParameter formRuleTypeParameter) {
		FormRuleTypeParameter updateFormRuleTypeParameter =formRuleTypeParameterDao.findById(formRuleTypeParameter.getId()).get();
				
//		updateFormRuleTypeParameter.setFormRuleTypeParameterName(formRuleTypeParameter.getFormRuleTypeParameterName());
//		updateFormRuleTypeParameter.setFormRuleTypeParameterDesc(formRuleTypeParameter.getFormRuleTypeParameterDesc());
					
        return formRuleTypeParameterDao.save(updateFormRuleTypeParameter);
	}
	
	private boolean formRuleTypeParameterExist(String formRuleTypeParameterName) {
       
        if (formRuleTypeParameterDao.findByParameterName(formRuleTypeParameterName).isPresent()) {
            return true;
        }
        return false;
    }

}
