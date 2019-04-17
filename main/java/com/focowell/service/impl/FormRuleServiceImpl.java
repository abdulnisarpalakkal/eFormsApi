package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.FormRuleDao;
import com.focowell.dao.UserDao;
import com.focowell.model.FormRule;
import com.focowell.model.User;
import com.focowell.model.VirtualTableField;
import com.focowell.service.FormRuleService;

@Service(value = "formRuleService")
public class FormRuleServiceImpl implements FormRuleService {

	@Autowired
	private FormRuleDao formRuleDao;
	
	@Override
	public List<FormRule> findAll() {
		List<FormRule> list = new ArrayList<>();
		formRuleDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		formRuleDao.deleteById(id);
		
	}

	@Override
	public FormRule findOne(String formRuleName) {
		return formRuleDao.findByFormRuleName(formRuleName).get();
	}

	@Override
	public FormRule findById(Long id) {
		return formRuleDao.findById(id).get();
	}
	@Override
	public List<FormRule> findByFormId(Long formId) {
		List<FormRule> list = new ArrayList<>();
		formRuleDao.findByFormMaster_Id(formId).iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public FormRule save(FormRule formRule) throws AlreadyExistsException {
		if(formRuleExist(formRule.getFormRuleName()))
		{
			throw new AlreadyExistsException(
		              "There is an FormRule with the same name "
		              +  formRule.getFormRuleName() );
		}
		return formRuleDao.save(formRule);
	}
	@Override
	public List<FormRule> saveAll(List<FormRule> formRules) {
		List<FormRule> list = new ArrayList<>();
		formRuleDao.saveAll(formRules).iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public FormRule update(FormRule formRule) {
		FormRule updateFormRule =formRuleDao.findById(formRule.getId()).get();
				
//		updateFormRule.setFormRuleName(formRule.getFormRuleName());
//		updateFormRule.setFormRuleDesc(formRule.getFormRuleDesc());
					
        return formRuleDao.save(updateFormRule);
	}
	
	private boolean formRuleExist(String formRuleName) {
       
        if (formRuleDao.findByFormRuleName(formRuleName).isPresent() ) {
            return true;
        }
        return false;
    }

	

}
